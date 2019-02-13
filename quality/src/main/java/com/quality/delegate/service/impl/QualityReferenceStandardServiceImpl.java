package com.quality.delegate.service.impl;

import com.quality.delegate.entity.QualityReferenceStandard;
import com.quality.delegate.mapper.QualityReferenceStandardMapper;
import com.quality.delegate.service.IQualityReferenceStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 认可标准 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@Service
public class QualityReferenceStandardServiceImpl extends ServiceImpl<QualityReferenceStandardMapper, QualityReferenceStandard> implements IQualityReferenceStandardService {

    @Override
    public List<QualityReferenceStandard> queryByReferenceStandardIds(String[] referenceStandardIds) {
        return this.baseMapper.queryByReferenceStandardIds(referenceStandardIds);
    }
}
