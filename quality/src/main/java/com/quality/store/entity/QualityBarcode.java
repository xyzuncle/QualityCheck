package com.quality.store.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 条形码样品关系表
 * </p>
 *
 * @author yerui
 * @since 2019-01-23
 */
@ApiModel(value="QualityBarcode对象", description="条形码样品关系表")
public class QualityBarcode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "条形码编码")
    @TableField("barCode")
    private String barCode;

    @ApiModelProperty(value = "样品id编号")
    @TableField("sampleCode")
    private String sampleCode;

    @ApiModelProperty(value = "样品规格编号")
    @TableField("specifications")
    private String specifications;

    @ApiModelProperty(value = "样品名称")
    @TableField("sampleName")
    private String sampleName;

    @ApiModelProperty(value = "厂家名称")
    @TableField("manufacturerName")
    private String manufacturerName;

    @ApiModelProperty(value = "厂家编号")
    @TableField("manufacturerCode")
    private String manufacturerCode;

    @ApiModelProperty(value = "条形码图片路径")
    @TableField("barCodeImgPath")
    private String barCodeImgPath;

    @ApiModelProperty(value = "样品型号")
    @TableField("sampleModel")
    private String sampleModel;

    public String getSampleModel() {
        return sampleModel;
    }

    public void setSampleModel(String sampleModel) {
        this.sampleModel = sampleModel;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getBarCodeImgPath() {
        return barCodeImgPath;
    }

    public void setBarCodeImgPath(String barCodeImgPath) {
        this.barCodeImgPath = barCodeImgPath;
    }

    @Override
    public String toString() {
        return "QualityBarcode{" +
        "barCode=" + barCode +
        ", sampleCode=" + sampleCode +
        ", specifications=" + specifications +
        ", sampleName=" + sampleName +
        ", manufacturerName=" + manufacturerName +
        ", manufacturerCode=" + manufacturerCode +
        ", barCodeImgPath=" + barCodeImgPath +
        "}";
    }
}
