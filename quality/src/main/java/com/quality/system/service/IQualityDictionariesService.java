package com.quality.system.service;

import com.quality.system.entity.QualityDictionaries;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author yerui
 * @since 2018-11-26
 */
public interface IQualityDictionariesService extends IService<QualityDictionaries> {

    List<Map<String,String>> queryByTypes(List<String> list);
}
