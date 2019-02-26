package com.quality.system.service;

import com.quality.common.entity.QualityAttachment;
import com.quality.system.entity.QualityTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */
public interface IQualityTemplateService extends IService<QualityTemplate> {

    public void customSave(QualityTemplate qualityTemplate);

    public boolean customRemoveById(String EntityId,String attachmentId);
}
