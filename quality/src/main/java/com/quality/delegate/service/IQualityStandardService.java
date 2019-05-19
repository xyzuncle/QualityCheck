package com.quality.delegate.service;

import com.quality.delegate.entity.QualityStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标准器表 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
public interface IQualityStandardService extends IService<QualityStandard> {

    List<QualityStandard> queryByCheckAbilityId(String checkAbilityId);
}
