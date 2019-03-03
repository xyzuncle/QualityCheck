package com.quality.delegate.service.impl;

import com.quality.common.dto.PageResult;
import com.quality.common.util.BeanCopierUtils;
import com.quality.common.util.Tools;
import com.quality.delegate.dto.QualityReportDto;
import com.quality.delegate.entity.QualityCheckAbility;
import com.quality.delegate.entity.QualityReport;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.entity.QualityTask;
import com.quality.delegate.mapper.QualityReportMapper;
import com.quality.delegate.service.IQualityCheckAbilityService;
import com.quality.delegate.service.IQualityReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.delegate.service.IQualitySampleService;
import com.quality.delegate.service.IQualityTaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-03-02
 */
@Service
public class QualityReportServiceImpl extends ServiceImpl<QualityReportMapper, QualityReport> implements IQualityReportService {

    @Autowired
    private IQualitySampleService sampleService;

    @Autowired
    private IQualityTaskService taskService;

    @Autowired
    private IQualityCheckAbilityService checkAbilityService;


    @Override
    public PageResult<QualityReportDto> converQualityReportDto(PageResult<QualityReport> qualityReportPage) {

        PageResult<QualityReportDto> qualityReportDtotListPage = new PageResult<QualityReportDto>();
        List<QualityReport> list = qualityReportPage.getData();

        List<QualityReportDto> dtolist = new ArrayList<QualityReportDto>();

        for(int i=0;i<list.size();i++){

            QualityReport report = list.get(i);
            QualityReportDto dto = new QualityReportDto();
            BeanCopierUtils.copyProperties(report, dto);

            //样品
            String sampleId = report.getSampleId();
            if(StringUtils.isNotBlank(sampleId)){
                 QualitySample sample = sampleService.getById(sampleId);
                 dto.setSampleName(sample.getSampleName());
            }
            //查询任务
            String taskId = report.getTaskId();
            if(StringUtils.isNotBlank(taskId)){
                QualityTask task = taskService.getById(taskId);
                dto.setDelegateUnit(task.getDelegateUnit());
                dto.setTaskIssuedBy(task.getTaskIssuedBy());
                dto.setTaskIssuedDate(task.getTaskIssuedDate());

            }

            //查询校验能力
            String checkAbilityID = report.getCheckAbilityIDs();
            String checkAbilityName="";
            String[] ids = Tools.str2StrArray(checkAbilityID);
            for(int j=0;j<ids.length;j++){
                  String id = ids[j];
                  QualityCheckAbility checkAbility = checkAbilityService.getById(id);
                if(checkAbility!=null){
                    checkAbilityName += checkAbility.getSpecificationCName()+",";
                }
            }
            dto.setCheckAbilityName(checkAbilityName.substring(0,checkAbilityName.length()-1));
            dtolist.add(dto);

        }
        qualityReportDtotListPage.setCount(dtolist.size());
        qualityReportDtotListPage.setData(dtolist);
        return qualityReportDtotListPage;
    }
}
