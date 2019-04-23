package com.quality.delegate.service;

import com.quality.delegate.entity.QualityDelegateunit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 委托单位 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface IQualityDelegateunitService extends IService<QualityDelegateunit> {

    List<Map<String,String>> queryByMap();
}
