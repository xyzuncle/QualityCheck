package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualityAssignmentStatement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 委托协议书 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface QualityAssignmentStatementMapper extends BaseMapper<QualityAssignmentStatement> {

    QualityAssignmentStatement getByAgreementNo(@Param(value = "agreementNo") String agreementNo);
}
