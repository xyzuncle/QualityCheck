package com.quality.store.service;

import com.quality.store.entity.QualityBarcode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 条形码样品关系表 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-23
 */
public interface IQualityBarcodeService extends IService<QualityBarcode> {
    public boolean SaveBarCodeAndImg(QualityBarcode qualityBarcode);


}
