package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualitySampleAbility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-05-19
 */
public interface QualitySampleAbilityMapper extends BaseMapper<QualitySampleAbility> {

    boolean deleteBySampleId(@Param(value = "sampleId") String sampleId);

    List<QualitySampleAbility> queryBySampleId(@Param(value = "sampleId") String sampleId);
}
