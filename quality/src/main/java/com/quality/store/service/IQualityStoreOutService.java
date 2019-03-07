package com.quality.store.service;

import com.quality.store.entity.QualityStoreOut;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-27
 */
public interface IQualityStoreOutService extends IService<QualityStoreOut> {

    public void customSave(QualityStoreOut qualityStoreOut);

}
