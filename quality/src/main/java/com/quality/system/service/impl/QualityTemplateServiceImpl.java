package com.quality.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.entity.QualityAttachment;
import com.quality.common.service.impl.QualityAttachmentServiceImpl;
import com.quality.delegate.service.impl.QualitySampleServiceImpl;
import com.quality.system.entity.QualityTemplate;
import com.quality.system.mapper.QualityTemplateMapper;
import com.quality.system.service.IQualityTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */
@Service
public class QualityTemplateServiceImpl extends ServiceImpl<QualityTemplateMapper, QualityTemplate> implements IQualityTemplateService {

    @Autowired
    QualityAttachmentServiceImpl qualityAttachmentService;

    @Override
    public void customSave(QualityTemplate qualityTemplate) {

        this.saveOrUpdate(qualityTemplate);
        String id = qualityTemplate.getId();
        if(StringUtils.isNotBlank(id)){
            String attachmentId = qualityTemplate.getAttachmentId();
            QueryWrapper<QualityAttachment> ew = new QueryWrapper<>();
            ew.eq("id", attachmentId);
            QualityAttachment attachment = qualityAttachmentService.getOne(ew);
            //更新业务关联id
            attachment.setBusinessId(id);
            qualityAttachmentService.saveOrUpdate(attachment);
        }


        //保存或者修改先更新附件表的信息

    }

    @Override
    public boolean customRemoveById(String EntityId,String attachmentId) {
        boolean result = true;
        try {
            if(StringUtils.isNotBlank(EntityId)&&StringUtils.isNotBlank(attachmentId)){
                qualityAttachmentService.custumDel(attachmentId);
                this.baseMapper.deleteById(EntityId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}