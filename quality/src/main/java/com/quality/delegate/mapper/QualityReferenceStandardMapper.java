package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualityReferenceStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 认可标准 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface QualityReferenceStandardMapper extends BaseMapper<QualityReferenceStandard> {

    List<QualityReferenceStandard> queryByReferenceStandardIds(String[] referenceStandardIds);
}
