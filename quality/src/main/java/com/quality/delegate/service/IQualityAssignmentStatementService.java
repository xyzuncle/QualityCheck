package com.quality.delegate.service;

import com.quality.common.dto.PageResult;
import com.quality.delegate.dto.QualityAssignmentStatementDto;
import com.quality.delegate.entity.QualityAssignmentStatement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 委托协议书 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface IQualityAssignmentStatementService extends IService<QualityAssignmentStatement> {


    public PageResult<QualityAssignmentStatementDto> converQualityAssignmentStatementDto(PageResult<QualityAssignmentStatement> qualityAssignmentStatementListPage);

    boolean saveOrUpdateDto(QualityAssignmentStatementDto QualityAssignmentStatementDto);

    QualityAssignmentStatementDto getByAgreementNo(String agreementNo);
}
