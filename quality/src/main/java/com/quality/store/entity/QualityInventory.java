package com.quality.store.entity;

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
 * @since 2019-03-05
 */
@ApiModel(value="QualityInventory对象", description="")
public class QualityInventory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "样品ID")
    @TableField("sampleId")
    private String sampleId;

    @ApiModelProperty(value = "样品总量")
    @TableField("sampleCount")
    private Integer sampleCount;

    @ApiModelProperty(value = "来样方式")
    @TableField("receivedType")
    private String receivedType;

    @ApiModelProperty(value = "交样人")
    @TableField("paySamplePerson")
    private String paySamplePerson;

    @ApiModelProperty(value = "收样人")
    @TableField("receiveSamplePerson")
    private String receiveSamplePerson;

    @ApiModelProperty(value = "样机名称")
    @TableField("sampleName")
    private String sampleName;

    @ApiModelProperty(value = "样机规格")
    @TableField("sampleSpecification")
    private String sampleSpecification;

    @ApiModelProperty(value = "样机型号")
    @TableField("sampleModel")
    private String sampleModel;

    @ApiModelProperty(value = "委托单位")
    @TableField("delegateUnitID")
    private String delegateUnitID;

    @ApiModelProperty(value = "委托单位名称")
    @TableField("delegateUnitName")
    private String delegateUnitName;

    @ApiModelProperty(value = "样品编号")
    @TableField("sampleCode")
    private String sampleCode;

    @ApiModelProperty(value = "1 标准器 2 委托样品")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "仓库名称")
    @TableField("storeName")
    private String storeName;


    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(Integer sampleCount) {
        this.sampleCount = sampleCount;
    }

    public String getReceivedType() {
        return receivedType;
    }

    public void setReceivedType(String receivedType) {
        this.receivedType = receivedType;
    }

    public String getPaySamplePerson() {
        return paySamplePerson;
    }

    public void setPaySamplePerson(String paySamplePerson) {
        this.paySamplePerson = paySamplePerson;
    }

    public String getReceiveSamplePerson() {
        return receiveSamplePerson;
    }

    public void setReceiveSamplePerson(String receiveSamplePerson) {
        this.receiveSamplePerson = receiveSamplePerson;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleSpecification() {
        return sampleSpecification;
    }

    public void setSampleSpecification(String sampleSpecification) {
        this.sampleSpecification = sampleSpecification;
    }

    public String getSampleModel() {
        return sampleModel;
    }

    public void setSampleModel(String sampleModel) {
        this.sampleModel = sampleModel;
    }

    public String getDelegateUnitID() {
        return delegateUnitID;
    }

    public void setDelegateUnitID(String delegateUnitID) {
        this.delegateUnitID = delegateUnitID;
    }

    public String getDelegateUnitName() {
        return delegateUnitName;
    }

    public void setDelegateUnitName(String delegateUnitName) {
        this.delegateUnitName = delegateUnitName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "QualityInventory{" +
        "sampleId=" + sampleId +
        ", sampleCount=" + sampleCount +
        ", receivedType=" + receivedType +
        ", paySamplePerson=" + paySamplePerson +
        ", receiveSamplePerson=" + receiveSamplePerson +
        ", sampleName=" + sampleName +
        ", sampleSpecification=" + sampleSpecification +
        ", sampleModel=" + sampleModel +
        ", delegateUnitID=" + delegateUnitID +
        ", delegateUnitName=" + delegateUnitName +
        ", sampleCode=" + sampleCode +
        ", type=" + type +
        ", storeName=" + storeName +
        "}";
    }
}
