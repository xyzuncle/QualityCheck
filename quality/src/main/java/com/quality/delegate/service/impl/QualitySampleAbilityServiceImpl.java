package com.quality.delegate.service.impl;

import com.quality.delegate.entity.QualitySampleAbility;
import com.quality.delegate.mapper.QualitySampleAbilityMapper;
import com.quality.delegate.service.IQualitySampleAbilityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-05-19
 */
@Service
public class QualitySampleAbilityServiceImpl extends ServiceImpl<QualitySampleAbilityMapper, QualitySampleAbility> implements IQualitySampleAbilityService {


    @Override
    public boolean deleteBySampleId(String sampleId) {
        return baseMapper.deleteBySampleId(sampleId);
    }

    @Override
    public List<QualitySampleAbility> queryBySampleId(String sampleId) {
        return baseMapper.queryBySampleId(sampleId);
    }


}
