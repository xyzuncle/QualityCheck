package com.quality.common.service;

import com.quality.common.entity.QualityAttachment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */
public interface IQualityAttachmentService extends IService<QualityAttachment> {

    public String custumSave(QualityAttachment qualityAttachment, MultipartFile file, HttpServletRequest request);

    public boolean custumDel(String attachmentId);

}
