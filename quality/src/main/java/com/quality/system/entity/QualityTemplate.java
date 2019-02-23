package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */
@ApiModel(value="QualityTemplate对象", description="")
public class QualityTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板名称")
    @TableField("templateName")
    private String templateName;

    @ApiModelProperty(value = "模板描述")
    @TableField("templateDESC")
    private String templateDESC;

    @ApiModelProperty(value = "上传人")
    @TableField("uploadUser")
    private String uploadUser;

    @ApiModelProperty(value = "模板类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "附件id")
    @TableField("attachmentId")
    private String attachmentId;


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDESC() {
        return templateDESC;
    }

    public void setTemplateDESC(String templateDESC) {
        this.templateDESC = templateDESC;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Override
    public String toString() {
        return "QualityTemplate{" +
        "templateName=" + templateName +
        ", templateDESC=" + templateDESC +
        ", uploadUser=" + uploadUser +
        ", type=" + type +
        ", attachmentId=" + attachmentId +
        "}";
    }
}
