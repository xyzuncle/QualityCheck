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
 * @since 2019-01-31
 */
@ApiModel(value="QualityStoreIn对象", description="")
public class QualityStoreIn extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "样品ID")
    @TableField("sampleId")
    private String sampleId;

    @ApiModelProperty(value = "样品编号")
    @TableField("sampleCode")
    private String sampleCode;

    @ApiModelProperty(value = "样品数量")
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

    @ApiModelProperty(value = "样品状态，0 损坏，1正常，2 返修")
    @TableField("sampleStatus")
    private String sampleStatus;

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

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

    @Override
    public String toString() {
        return "QualityStoreIn{" +
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
        "}";
    }
}
