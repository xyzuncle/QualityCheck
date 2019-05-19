package com.quality.delegate.service.impl;

import com.quality.delegate.entity.QualityStandard;
import com.quality.delegate.mapper.QualityStandardMapper;
import com.quality.delegate.service.IQualityStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标准器表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
@Service
public class QualityStandardServiceImpl extends ServiceImpl<QualityStandardMapper, QualityStandard> implements IQualityStandardService {

    @Override
    public List<QualityStandard> queryByCheckAbilityId(String checkAbilityId) {
        return baseMapper.queryByCheckAbilityId(checkAbilityId);
    }
}
