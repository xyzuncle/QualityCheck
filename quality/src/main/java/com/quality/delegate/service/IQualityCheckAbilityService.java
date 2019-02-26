package com.quality.delegate.service;

import com.quality.delegate.entity.QualityCheckAbility;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 校准能力 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
public interface IQualityCheckAbilityService extends IService<QualityCheckAbility> {

    List<QualityCheckAbility> queryByCheckAbilityIds(String[] checkAbilityIds);
}
