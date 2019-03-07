package com.quality.store.service;

import com.quality.store.entity.QualityStoreIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-31
 */
public interface IQualityStoreInService extends IService<QualityStoreIn> {

    public void customSave(QualityStoreIn qualityStoreIn);

}
