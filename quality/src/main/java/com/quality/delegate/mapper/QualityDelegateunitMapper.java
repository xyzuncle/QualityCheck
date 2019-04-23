package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualityDelegateunit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 委托单位 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface QualityDelegateunitMapper extends BaseMapper<QualityDelegateunit> {

    List<Map<String,String>> queryByMap();
}
