package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 样机信息
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@ApiModel(value="QualitySample对象", description="样机信息")
public class QualitySample extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "样品名称")
    @TableField("sampleName")
    private String sampleName;

    @ApiModelProperty(value = "样机编号")
    @TableField("sampleCode")
    private String sampleCode;

    @ApiModelProperty(value = "样机型号")
    @TableField("sampleModel")
    private String sampleModel;

    @ApiModelProperty(value = "样机规格")
    @TableField("sampleSpecification")
    private String sampleSpecification;

    @ApiModelProperty(value = "收样数量")
    @TableField("sampleNum")
    private Integer sampleNum;

    @ApiModelProperty(value = "收样日期")
    @TableField("receivedDate")
    private LocalDate receivedDate;

    @ApiModelProperty(value = "送样人")
    @TableField("sender")
    private String sender;

    @ApiModelProperty(value = "来样方式")
    @TableField("receivedType")
    private String receivedType;

    @ApiModelProperty(value = "单位名称")
    @TableField("unitName")
    private String unitName;

    @ApiModelProperty(value = "委托单位ID")
    @TableField("delegateUnitID")
    private String delegateUnitID;


    @ApiModelProperty(value = "实验室")
    @TableField("laboratory")
    private String laboratory;


    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSampleModel() {
        return sampleModel;
    }

    public void setSampleModel(String sampleModel) {
        this.sampleModel = sampleModel;
    }

    public String getSampleSpecification() {
        return sampleSpecification;
    }

    public void setSampleSpecification(String sampleSpecification) {
        this.sampleSpecification = sampleSpecification;
    }

    public Integer getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(Integer sampleNum) {
        this.sampleNum = sampleNum;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivedType() {
        return receivedType;
    }

    public void setReceivedType(String receivedType) {
        this.receivedType = receivedType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDelegateUnitID() {
        return delegateUnitID;
    }

    public void setDelegateUnitID(String delegateUnitID) {
        this.delegateUnitID = delegateUnitID;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    @Override
    public String toString() {
        return "QualitySample{" +
        "sampleName=" + sampleName +
        ", sampleCode=" + sampleCode +
        ", sampleModel=" + sampleModel +
        ", sampleSpecification=" + sampleSpecification +
        ", sampleNum=" + sampleNum +
        ", unitName=" + unitName +
        ", delegateUnitID=" + delegateUnitID +
        ", laboratory=" + laboratory +
        ", receivedDate=" + receivedDate +
        ", sender=" + sender +
        ", receivedType=" + receivedType +
        "}";
    }
}
