package com.quality.common.entity;

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
@ApiModel(value="QualityAttachment对象", description="")
public class QualityAttachment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属模块或业务ID")
    @TableField("businessId")
    private String businessId;

    @ApiModelProperty(value = "fastfds附件的路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "缩列图的路径")
    @TableField("smpath")
    private String smpath;


    @ApiModelProperty(value = "附件名称")
    @TableField("fileName")
    private String fileName;

    @ApiModelProperty(value = "缩列图的宽")
    @TableField("smImgWidth")
    private String smImgWidth;

    @ApiModelProperty(value = "缩列图的高")
    @TableField("smImgHeight")
    private String smImgHeight;

    @ApiModelProperty(value = "文件类型")
    @TableField("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSmpath() {
        return smpath;
    }

    public void setSmpath(String smpath) {
        this.smpath = smpath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSmImgWidth() {
        return smImgWidth;
    }

    public void setSmImgWidth(String smImgWidth) {
        this.smImgWidth = smImgWidth;
    }

    public String getSmImgHeight() {
        return smImgHeight;
    }

    public void setSmImgHeight(String smImgHeight) {
        this.smImgHeight = smImgHeight;
    }

    @Override
    public String toString() {
        return "QualityAttachment{" +
        "businessId=" + businessId +
        ", path=" + path +
        ", smpath=" + smpath +
        ", fileName=" + fileName +
        "}";
    }
}
