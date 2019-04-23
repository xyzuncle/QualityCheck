package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 标准器表
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
@ApiModel(value="QualityStandard对象", description="标准器表")
public class QualityStandard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标准器名称")
    @TableField("standardName")
    private String standardName;

    @ApiModelProperty(value = "型号规格")
    @TableField("specifications")
    private String specifications;

    @ApiModelProperty(value = "仪器编号")
    @TableField("instrumentCode")
    private String instrumentCode;

    @ApiModelProperty(value = "证书附件")
    @TableField("certificateAttachment")
    private String certificateAttachment;

    @ApiModelProperty(value = "证书编号")
    @TableField("certificateCode")
    private String certificateCode;

    @ApiModelProperty(value = "不确定度")
    @TableField("uncertainty")
    private String uncertainty;

    @ApiModelProperty(value = "计量日期")
    @TableField("measurementDate")
    private LocalDate measurementDate;

    @ApiModelProperty(value = "有效期")
    @TableField("validityDate")
    private LocalDate validityDate;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "修改者")
    @TableField("updUser")
    private String updUser;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;


    @ApiModelProperty(value = "能力名称")
    private String checkAbilityName;

    @ApiModelProperty(value = "能力ID")
    private String checkAbilityId;


    public String getCheckAbilityName() {
        return checkAbilityName;
    }

    public void setCheckAbilityName(String checkAbilityName) {
        this.checkAbilityName = checkAbilityName;
    }

    public String getCheckAbilityId() {
        return checkAbilityId;
    }

    public void setCheckAbilityId(String checkAbilityId) {
        this.checkAbilityId = checkAbilityId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getCertificateAttachment() {
        return certificateAttachment;
    }

    public void setCertificateAttachment(String certificateAttachment) {
        this.certificateAttachment = certificateAttachment;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getUncertainty() {
        return uncertainty;
    }

    public void setUncertainty(String uncertainty) {
        this.uncertainty = uncertainty;
    }

    public LocalDate getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "QualityStandard{" +
                "standardName='" + standardName + '\'' +
                ", specifications='" + specifications + '\'' +
                ", instrumentCode='" + instrumentCode + '\'' +
                ", certificateAttachment='" + certificateAttachment + '\'' +
                ", certificateCode='" + certificateCode + '\'' +
                ", uncertainty='" + uncertainty + '\'' +
                ", measurementDate=" + measurementDate +
                ", validityDate=" + validityDate +
                ", status='" + status + '\'' +
                ", crtUser='" + crtUser + '\'' +
                ", updUser='" + updUser + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
