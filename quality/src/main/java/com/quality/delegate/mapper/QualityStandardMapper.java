package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualityStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标准器表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
public interface QualityStandardMapper extends BaseMapper<QualityStandard> {

    List<QualityStandard> queryByCheckAbilityId(@Param(value = "checkAbilityId") String checkAbilityId);
}
