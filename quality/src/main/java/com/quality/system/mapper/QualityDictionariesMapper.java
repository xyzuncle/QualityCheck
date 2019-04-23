package com.quality.system.mapper;

import com.quality.system.entity.QualityDictionaries;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-11-26
 */
public interface QualityDictionariesMapper extends BaseMapper<QualityDictionaries> {

    List<Map<String,String>> queryByTypes(List<String> list);
}
