package com.quality.delegate.service.impl;

import com.quality.delegate.entity.QualityCheckAbility;
import com.quality.delegate.mapper.QualityCheckAbilityMapper;
import com.quality.delegate.service.IQualityCheckAbilityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 校准能力 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
@Service
public class QualityCheckAbilityServiceImpl extends ServiceImpl<QualityCheckAbilityMapper, QualityCheckAbility> implements IQualityCheckAbilityService {

    @Override
    public List<QualityCheckAbility> queryByCheckAbilityIds(String[] checkAbilityIds) {
        return baseMapper.queryByCheckAbilityIds(checkAbilityIds);
    }

    @Override
    public List<Map<String, String>> queryByMap() {
        return baseMapper.queryByMap();
    }
}
