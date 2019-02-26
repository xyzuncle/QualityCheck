package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualityCheckAbility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 校准能力 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
public interface QualityCheckAbilityMapper extends BaseMapper<QualityCheckAbility> {

    List<QualityCheckAbility> queryByCheckAbilityIds(String[] checkAbilityIds);
}
