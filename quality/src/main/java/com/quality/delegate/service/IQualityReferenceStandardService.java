package com.quality.delegate.service;

import com.quality.delegate.entity.QualityReferenceStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 认可标准 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
public interface IQualityReferenceStandardService extends IService<QualityReferenceStandard> {

    List<QualityReferenceStandard> queryByReferenceStandardIds(String[] referenceStandardIds);
}
