package com.quality.delegate.service.impl;

import com.quality.common.constants.CommonConstants;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.service.ICommonTicketService;
import com.quality.common.util.BeanCopierUtils;
import com.quality.common.util.Tools;
import com.quality.delegate.dto.QualityAssignmentStatementDto;
import com.quality.delegate.entity.QualityAssignmentStatement;
import com.quality.delegate.entity.QualityDelegateunit;
import com.quality.delegate.entity.QualityReferenceStandard;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.mapper.QualityAssignmentStatementMapper;
import com.quality.delegate.service.IQualityAssignmentStatementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.delegate.service.IQualityDelegateunitService;
import com.quality.delegate.service.IQualityReferenceStandardService;
import com.quality.delegate.service.IQualitySampleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private IQualityReferenceStandardService  referenceStandardService;


    @Autowired
    private ICommonTicketService commonTicketService;


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

        for(int i=0;i<list.size();i++){

            QualityAssignmentStatement assignmentStatement = list.get(i);
            QualityAssignmentStatementDto dto = new QualityAssignmentStatementDto();
            BeanCopierUtils.copyProperties(assignmentStatement, dto);
            dto.setAssignmentId(assignmentStatement.getId());
            //查询委托单位信息
            String delegateUnitID = assignmentStatement.getDelegateUnitID();
            QualityDelegateunit delegateunit = delegateunitService.getById(delegateUnitID);
            if(delegateunit!=null){
                BeanCopierUtils.copyProperties(delegateunit, dto);
                dto.setUnitId(delegateunit.getId());

            }

            dtolist.add(dto);

        }

        /*list.forEach(item -> {
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
        }*/
        QualityAssignmentStatemenDtotListPage.setCount(dtolist.size());
        QualityAssignmentStatemenDtotListPage.setData(dtolist);
        return QualityAssignmentStatemenDtotListPage;
    }

    @Transactional(rollbackFor = BaseException.class)
    public boolean saveOrUpdateDto(QualityAssignmentStatementDto dto) {
        boolean result = true;
        QualityAssignmentStatement qualityAssignmentStatement = new QualityAssignmentStatement();
        qualityAssignmentStatement.setState(CommonConstants.START_ENTRY);  //插入初始状态
        BeanCopierUtils.copyProperties(dto, qualityAssignmentStatement);

        QualityDelegateunit delegateunit = new QualityDelegateunit();
        BeanCopierUtils.copyProperties(dto, delegateunit);
        //委托单位
        if(result){
            String unitId = dto.getUnitId();
            if(StringUtils.isNotBlank(unitId)){
                delegateunit.setId(unitId);
            }
            result = delegateunitService.saveOrUpdate(delegateunit);
        }

        if(result){

            qualityAssignmentStatement.setDelegateUnitID(dto.getUnitId());
            String assignmentId = dto.getAssignmentId();
            if(StringUtils.isNotBlank(assignmentId)){
                qualityAssignmentStatement.setId(assignmentId);
            }else{
                String unitId = qualityAssignmentStatement.getDelegateUnitID();
                if(StringUtils.isNotBlank(unitId)){
                    QualityDelegateunit delegeunit = delegateunitService.getById(unitId);
                    String unitCode = delegeunit.getUnitCode();
                    String sampleCode = commonTicketService.selectAssignmentCode(unitCode);
                    qualityAssignmentStatement.setAgreementNo(sampleCode);
                }
            }


            result = this.saveOrUpdate(qualityAssignmentStatement);
        }

        return result;
    }

    @Override
    public QualityAssignmentStatementDto getByAgreementNo(String agreementNo) {

        QualityAssignmentStatementDto dto = new QualityAssignmentStatementDto();

        //根据协议编号查询
        QualityAssignmentStatement assignmentStatement = this.baseMapper.getByAgreementNo(agreementNo);
        BeanCopierUtils.copyProperties(assignmentStatement, dto);
        dto.setAssignmentId(assignmentStatement.getId());
        //查询委托单位信息
        String delegateUnitID = assignmentStatement.getDelegateUnitID();
        QualityDelegateunit delegateunit = delegateunitService.getById(delegateUnitID);
        BeanCopierUtils.copyProperties(delegateunit, dto);
        dto.setUnitId(delegateunit.getId());

        return dto;
    }
}
