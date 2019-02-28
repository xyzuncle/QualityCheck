package com.quality.delegate.service.impl;

import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.util.BeanCopierUtils;
import com.quality.common.util.Tools;
import com.quality.delegate.dto.QualityTaskDto;
import com.quality.delegate.entity.QualityCheckAbility;
import com.quality.delegate.entity.QualityDelegateunit;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.entity.QualityTask;
import com.quality.delegate.mapper.QualityTaskMapper;
import com.quality.delegate.service.IQualityCheckAbilityService;
import com.quality.delegate.service.IQualityDelegateunitService;
import com.quality.delegate.service.IQualitySampleService;
import com.quality.delegate.service.IQualityTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.system.entity.QualityUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private IQualitySampleService sampleService;

    @Autowired
    private IQualityDelegateunitService delegateunitService;

    @Autowired
    private IQualityCheckAbilityService checkAbilityService;

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
                    }else{
                        result =  sampleService.saveOrUpdate(item);
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
                    if(checkAbilityid==null){
                        checkAbilityid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                        item.setId(checkAbilityid);
                        result =  checkAbilityService.save(item);
                    }else{
                        result =  checkAbilityService.saveOrUpdate(item);
                    }
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
                qualityTask.setTaskIssuedDate(LocalDate.now());
                qualityTask.setCrtTime(new Date());

            }else{
                qualityTask.setId(taskId);
            }
            result = this.saveOrUpdate(qualityTask);
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

}
