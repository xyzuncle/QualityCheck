package com.quality.delegate.service.impl;

import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
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
        BeanCopierUtils.copyProperties(dto, qualityAssignmentStatement);

        QualityDelegateunit delegateunit = new QualityDelegateunit();
        BeanCopierUtils.copyProperties(dto, delegateunit);
        String sampleIDs="",referenceStandardIds="";
        List<String> sampleids = new ArrayList<>();
        List<String> referenceStandardids = new ArrayList<>();
   
        //更新和保存样品信息
        if(result){
            List<QualitySample> samples = dto.getQualitySamples();
            samples.forEach(item->{
                String sampleid = item.getId() + "";
                if(sampleid==null){
                    sampleid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    item.setId(sampleid);
                    sampleService.save(item);
                }else{
                    sampleService.saveOrUpdate(item);
                }
                sampleids.add(sampleid);
            });


            result = sampleService.saveOrUpdateBatch(samples);
        }
        //更新和保存相关依据
        if(result){
            List<QualityReferenceStandard> referenceStandards = dto.getQualityReferenceStandards();
            referenceStandards.forEach(item->{
                String referenceStandardid = item.getId() + "";
                if(referenceStandardid==null){
                    referenceStandardid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    item.setId(referenceStandardid);
                    referenceStandardService.save(item);
                }else{
                    referenceStandardService.saveOrUpdate(item);
                }
                referenceStandardids.add(referenceStandardid);
            });


            result = referenceStandardService.saveOrUpdateBatch(referenceStandards);
        }
        //委托单位
        if(result){
            String unitId = dto.getUnitId();
            if(StringUtils.isNotBlank(unitId)){
                delegateunit.setId(unitId);
            }
            result = delegateunitService.saveOrUpdate(delegateunit);
        }

        if(result){

            sampleIDs = Tools.listToString(sampleids);
            referenceStandardIds = Tools.listToString(referenceStandardids);
            qualityAssignmentStatement.setSampleIDs(sampleIDs);
            qualityAssignmentStatement.setReferenceStandardIds(referenceStandardIds);
            qualityAssignmentStatement.setDelegateUnitID(dto.getUnitId());

            String assignmentId = dto.getAssignmentId();
            if(StringUtils.isNotBlank(assignmentId)){
                qualityAssignmentStatement.setId(assignmentId);
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
        dto.setAssignmentId(assignmentStatement.getId());
        //查询委托单位信息
        String delegateUnitID = assignmentStatement.getDelegateUnitID();
        QualityDelegateunit delegateunit = delegateunitService.getById(delegateUnitID);
        BeanCopierUtils.copyProperties(delegateunit, dto);
        dto.setUnitId(delegateunit.getId());

        String sampleids = assignmentStatement.getSampleIDs();
        String referenceStandardIds= assignmentStatement.getReferenceStandardIds();

        //查询样品信息
        String[] sampleIds = Tools.str2StrArray(sampleids);
        List<QualitySample> sampleList = sampleService.queryBySampleIds(sampleIds);


        //查询参考规范
        String[] referenceStandardids = Tools.str2StrArray(referenceStandardIds);
        List<QualityReferenceStandard> referenceStandardList = referenceStandardService.queryByReferenceStandardIds(referenceStandardids);
        BeanCopierUtils.copyProperties(assignmentStatement, dto);

        dto.setQualitySamples(sampleList);
        dto.setQualityReferenceStandards(referenceStandardList);

        return dto;
    }
}
