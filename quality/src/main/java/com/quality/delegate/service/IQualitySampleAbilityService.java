package com.quality.delegate.service;

import com.quality.delegate.entity.QualitySampleAbility;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-05-19
 */
public interface IQualitySampleAbilityService extends IService<QualitySampleAbility> {

    boolean deleteBySampleId(String sampleId);

    List<QualitySampleAbility> queryBySampleId(String sampleId);
}
