package com.quality.system.service.impl;

import com.quality.system.entity.QualityDictionaries;
import com.quality.system.mapper.QualityDictionariesMapper;
import com.quality.system.service.IQualityDictionariesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-11-26
 */
@Service
public class QualityDictionariesServiceImpl extends ServiceImpl<QualityDictionariesMapper, QualityDictionaries> implements IQualityDictionariesService {

    @Override
    public List<Map<String, String>> queryByTypes(List<String> list) {
        return baseMapper.queryByTypes(list);
    }
}
