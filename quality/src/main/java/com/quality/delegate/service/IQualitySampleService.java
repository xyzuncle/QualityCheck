package com.quality.delegate.service;

import com.quality.delegate.entity.QualitySample;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 样机信息 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface IQualitySampleService extends IService<QualitySample> {


    List<QualitySample> queryBySampleIds(String[] sampleIds);
}
