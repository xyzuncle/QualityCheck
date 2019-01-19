package com.quality.delegate.service.impl;

import com.quality.common.dto.PageResult;
import com.quality.delegate.entity.QualityAssignmentStatement;
import com.quality.delegate.entity.QualityAssignmentStatementDto;
import com.quality.delegate.entity.QualityDelegateunit;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.mapper.QualityAssignmentStatementMapper;
import com.quality.delegate.service.IQualityAssignmentStatementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.delegate.service.IQualityDelegateunitService;
import com.quality.delegate.service.IQualitySampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 委托协议书 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@Service
public class QualityAssignmentStatementServiceImpl extends ServiceImpl<QualityAssignmentStatementMapper, QualityAssignmentStatement> implements IQualityAssignmentStatementService {

    @Autowired
    private IQualitySampleService sampleService;

    @Autowired
    private IQualityDelegateunitService delegateunitService;


    /**
     * 转换
     *
     * @param qualityAssignmentStatementListPage
     * @return
     */
    public PageResult<QualityAssignmentStatementDto> converQualityAssignmentStatementDto(PageResult<QualityAssignmentStatement> qualityAssignmentStatementListPage) {

        PageResult<QualityAssignmentStatementDto> QualityAssignmentStatemenDtotListPage = new PageResult<QualityAssignmentStatementDto>();
        List<QualityAssignmentStatement> list = qualityAssignmentStatementListPage.getData();

        List<QualityAssignmentStatementDto> dtolist = new ArrayList<QualityAssignmentStatementDto>();
        list.forEach(item -> {
            QualityAssignmentStatementDto dto = new QualityAssignmentStatementDto();
            dto.setAgreementNo(item.getAgreementNo());
            dto.setDelegateType(item.getDelegateType());
            dto.setDelegateUnit(item.getDelegateUnitID());
            dto.setSample(item.getSampleID());
            dtolist.add(dto);
        });


        for (QualityAssignmentStatementDto dto : dtolist) {
            String sampleId = dto.getSample();
            String delegateUnitId = dto.getDelegateUnit();
            QualitySample sample = sampleService.getById(sampleId);
            QualityDelegateunit delegateunit=delegateunitService.getById(delegateUnitId);
            dto.setSample(sample.getSampleName());
            dto.setDelegateUnit(delegateunit.getUnitName());
        }
        QualityAssignmentStatemenDtotListPage.setCount(dtolist.size());
        QualityAssignmentStatemenDtotListPage.setData(dtolist);
        return QualityAssignmentStatemenDtotListPage;
    }
}
