package com.quality.delegate.service;

import com.quality.delegate.entity.QualityCheckAbility;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    List<Map<String,String>>  queryByMap();
}
