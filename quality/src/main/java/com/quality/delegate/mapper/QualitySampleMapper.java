package com.quality.delegate.mapper;

import com.quality.delegate.entity.QualitySample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 样机信息 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface QualitySampleMapper extends BaseMapper<QualitySample> {

    List<QualitySample> queryBySampleIds(String[] sampleIds);
}
