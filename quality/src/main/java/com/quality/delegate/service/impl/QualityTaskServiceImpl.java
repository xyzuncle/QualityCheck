package com.quality.delegate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.service.ICommonTicketService;
import com.quality.common.util.BeanCopierUtils;
import com.quality.common.util.Tools;
import com.quality.delegate.dto.QualityTaskDto;
import com.quality.delegate.dto.QualityTaskListDto;
import com.quality.delegate.entity.*;
import com.quality.delegate.mapper.QualityTaskMapper;
import com.quality.delegate.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.system.entity.QualityMenu;
import com.quality.system.entity.QualityUser;
import com.quality.system.service.IQualityUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 任务单 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-24
 */
@Service
public class QualityTaskServiceImpl extends ServiceImpl<QualityTaskMapper, QualityTask> implements IQualityTaskService {



    @Autowired
    private IQualityDelegateunitService delegateunitService;

    @Autowired
    private  IQualityReportService  reportService;

    @Autowired
    private IQualityUserService userService;
    @Autowired
    private IQualitySampleService sampleService;
    @Autowired
    private IQualitySampleAbilityService sampleAbilityService;
    @Autowired
    private IQualityCheckAbilityService  checkAbilityService;
    @Autowired
    private IQualityStandardService  qualityStandardService;

    @Autowired
    private ICommonTicketService commonTicketService;
    /**
     * 转换
     *
     * @param qualityTaskPage
     * @return
     */
    public PageResult<QualityTaskDto> converQualityTaskDto(PageResult<QualityTask> qualityTaskPage) {

        PageResult<QualityTaskDto> qualityTaskDtoPage = new PageResult<QualityTaskDto>();
        List<QualityTask> list = qualityTaskPage.getData();

        List<QualityTaskDto> dtolist = new ArrayList<QualityTaskDto>();

        for(int i=0;i<list.size();i++){

            QualityTask task = list.get(i);
            QualityTaskDto dto = new QualityTaskDto();
            dto.setTaskId(task.getId());
            String sampleids = task.getSampleIDs();
            String checkAbilityids= task.getCheckAbilityIDs();

            if(StringUtils.isNotBlank(sampleids)){
                //查询样品信息
                String[] sampleIds = Tools.str2StrArray(sampleids);
                List<QualitySample> sampleList = sampleService.queryBySampleIds(sampleIds);
                dto.setQualitySamples(sampleList);
            }

            if(StringUtils.isNotBlank(checkAbilityids)){
                //查询校验能力
                String[] checkAbilityIds = Tools.str2StrArray(checkAbilityids);
                List<QualityCheckAbility> checkAbilities = checkAbilityService.queryByCheckAbilityIds(checkAbilityIds);
                dto.setQualityCheckAbilities(checkAbilities);
            }
            BeanCopierUtils.copyProperties(task, dto);

            dtolist.add(dto);

        }

        qualityTaskDtoPage.setCount(dtolist.size());
        qualityTaskDtoPage.setData(dtolist);
        return qualityTaskDtoPage;
    }




    @Transactional(rollbackFor = BaseException.class)
    public boolean saveOrUpdateTask(QualityTaskDto dto) {
        boolean result = true;
        QualityTask qualityTask = new QualityTask();
        BeanCopierUtils.copyProperties(dto, qualityTask);

        String sampleIDs="",checkAbilityIds="";
        List<String> sampleids = new ArrayList<>();
        List<String> checkAbilityids = new ArrayList<>();


        //更新和保存样品信息
        if(result){
            List<QualitySample> samples = dto.getQualitySamples();
            if(samples !=null){
                for(int i=0;i<samples.size();i++){
                    QualitySample item =samples.get(i);
                    String sampleid = item.getId() + "";
                    if(sampleid==null){
                        sampleid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                        item.setId(sampleid);
                        result =  sampleService.save(item);
                    }
                    sampleids.add(sampleid);
                }
            }
        }
        //更新和保存相关依据
        if(result){
            List<QualityCheckAbility> checkAbilities = dto.getQualityCheckAbilities();
            if(checkAbilities!=null){
                for(int i=0;i<checkAbilities.size();i++){
                    QualityCheckAbility item =checkAbilities.get(i);
                    String checkAbilityid = item.getId() + "";
                    checkAbilityids.add(checkAbilityid);
                }

            }
        }
        if(result){
            sampleIDs = Tools.listToString(sampleids);
            checkAbilityIds = Tools.listToString(checkAbilityids);
            qualityTask.setSampleIDs(sampleIDs);
            qualityTask.setCheckAbilityIDs(checkAbilityIds);
            String taskId = dto.getTaskId();
            if(StringUtils.isBlank(taskId)){
                taskId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                qualityTask.setId(taskId);
                //查询当前用户
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                QualityUser user = (QualityUser)authentication.getPrincipal();
                qualityTask.setTaskIssuedBy(user.getUsername());
                qualityTask.setTaskIssuedDate(new Date());
                qualityTask.setCrtTime(new Date());
                String taskCode = commonTicketService.selectAssignmentCode(dto.getAgreementNo());
                qualityTask.setTaskCode(taskCode);


            }else{
                qualityTask.setId(taskId);
            }
            result = this.saveOrUpdate(qualityTask);

            //插入报告数据
            if(result){
                if(sampleids.size()>0){
                    sampleids.forEach(item->{
                        QualityReport  report = new QualityReport();
                        report.setAgreementNo(dto.getAgreementNo());
                        report.setTaskId(dto.getTaskId());
                        report.setCheckAbilityIDs(Tools.listToString(checkAbilityids));
                        report.setSampleId(item);
                        QueryWrapper<QualityReport> spec = new QueryWrapper<>();
                        spec.eq("taskId",dto.getTaskId());
                        spec.eq("agreementNo",dto.getAgreementNo());
                        spec.eq("sampleId",item);
                        QualityReport rep = reportService.getOne(spec);
                        if(rep ==null){
                            reportService.saveOrUpdate(report);
                        }

                    });
                }

            }
        }

        return result;
    }

    @Override
    public QualityTaskDto converTaskDto(QualityTask qualityTask) {
        QualityTaskDto dto = new QualityTaskDto();
        dto.setTaskId(qualityTask.getId());
        String sampleids = qualityTask.getSampleIDs();
        String checkAbilityids= qualityTask.getCheckAbilityIDs();

        if(StringUtils.isNotBlank(sampleids)){
            //查询样品信息
            String[] sampleIds = Tools.str2StrArray(sampleids);
            List<QualitySample> sampleList = sampleService.queryBySampleIds(sampleIds);
            dto.setQualitySamples(sampleList);
        }

        if(StringUtils.isNotBlank(checkAbilityids)){
            //查询校验能力
            String[] checkAbilityIds = Tools.str2StrArray(checkAbilityids);
            List<QualityCheckAbility> checkAbilities = checkAbilityService.queryByCheckAbilityIds(checkAbilityIds);
            dto.setQualityCheckAbilities(checkAbilities);
        }
        BeanCopierUtils.copyProperties(qualityTask, dto);


        return dto;
    }

    @Override
    public PageResult<QualityTaskListDto> converTaskPage(PageResult<QualityTask> qualityTaskPage) {
        PageResult<QualityTaskListDto> qualityTaskListPage = new PageResult<>();
        List<QualityTaskListDto>  listDtos = new ArrayList<>();
        List<QualityTask> taskList = qualityTaskPage.getData();

        if (taskList.size()>0){
            taskList.forEach(item->{
                QualityTaskListDto dto = new QualityTaskListDto();
                BeanCopierUtils.copyProperties(item, dto);
                dto.setParentId("0");
                listDtos.add(dto);
                //样品
                String sampleId = item.getSampleIDs();
                String[] sampleIds = Tools.str2StrArray(sampleId);
                for(int i=0;i<sampleIds.length;i++){
                    //在插入关系
                    QualitySample sample = sampleService.getById(sampleIds[i]);
                    QualityTaskListDto subdto = new QualityTaskListDto();
                    subdto.setParentId(item.getId());
                    subdto.setId(sample.getId());
                    subdto.setAgreementNo(sample.getSampleCode());
                    subdto.setDelegateUnit(sample.getSampleName());
                    listDtos.add(subdto);
                    //标准能力
                    List<QualitySampleAbility> checkAbilityList = sampleAbilityService.queryBySampleId(sample.getId());
                    for(int j=0;j<checkAbilityList.size();j++){
                        QualitySampleAbility sampleAbility = checkAbilityList.get(j);
                        QualityCheckAbility checkAbility = checkAbilityService.getById(sampleAbility.getCheckAbilityId());
                        QualityTaskListDto thdto = new QualityTaskListDto();
                        thdto.setParentId(sample.getId());
                        thdto.setId(checkAbility.getId());
                        thdto.setAgreementNo(checkAbility.getSpecificationEName());
                        thdto.setDelegateUnit(checkAbility.getSpecificationCName());
                        listDtos.add(thdto);
                        //标准器
                        List<QualityStandard> standardList = qualityStandardService.queryByCheckAbilityId(checkAbility.getId());
                        for(int k=0;k<standardList.size();k++){
                            QualityStandard standard =  standardList.get(i);
                            QualityTaskListDto fdto = new QualityTaskListDto();
                            fdto.setParentId(checkAbility.getId());
                            fdto.setId(standard.getId());
                            fdto.setAgreementNo(standard.getInstrumentCode());
                            fdto.setDelegateUnit(standard.getStandardName());
                            listDtos.add(fdto);
                        }
                    }

                }
            });
        }

        List<String> list = new ArrayList<>();
        List<QualityTaskListDto>  dtos = new ArrayList<>();
        for(int i=0;i<listDtos.size();i++){
            QualityTaskListDto qualityTaskListDto= listDtos.get(i);
            String id = qualityTaskListDto.getId();
            String parentId =  qualityTaskListDto.getParentId();
            String key =parentId+"#"+id;
            if(!list.contains(key)){
                list.add(key);
                dtos.add(qualityTaskListDto);
            }
        }
        qualityTaskListPage.setCount(qualityTaskPage.getCount());
        qualityTaskListPage.setData(dtos);
        qualityTaskListPage.setCode(qualityTaskPage.getCode());
        qualityTaskListPage.setPages(qualityTaskPage.getPages());
        return qualityTaskListPage;
    }
}
