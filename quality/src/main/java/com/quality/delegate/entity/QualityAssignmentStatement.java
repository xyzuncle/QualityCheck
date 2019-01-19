package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 委托协议书
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@ApiModel(value="QualityAssignmentStatement对象", description="委托协议书")
public class QualityAssignmentStatement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "协议书编号")
    @TableField("agreementNo")
    private String agreementNo;

    @ApiModelProperty(value = "委托类型")
    @TableField("delegateType")
    private String delegateType;

    @ApiModelProperty(value = "委托单位ID")
    @TableField("delegateUnitID")
    private String delegateUnitID;

    @ApiModelProperty(value = "样机ID")
    @TableField("sampleID")
    private String sampleID;

    @ApiModelProperty(value = "指定监测依据")
    @TableField("designatedMonitoringBasis")
    private String designatedMonitoringBasis;

    @ApiModelProperty(value = "指定监测项目")
    @TableField("designatedMonitoringItems")
    private String designatedMonitoringItems;

    @ApiModelProperty(value = "指定监测要求")
    @TableField("designatedMonitoringRequirement")
    private String designatedMonitoringRequirement;

    @ApiModelProperty(value = "受理评审类型")
    @TableField("acceptanceReviewType")
    private String acceptanceReviewType;

    @ApiModelProperty(value = "计划完成时间")
    @TableField("plannedCompletionTime")
    private String plannedCompletionTime;

    @ApiModelProperty(value = "是否保密")
    @TableField("secret")
    private String secret;

    @ApiModelProperty(value = "标准有效期")
    @TableField("standardEffectivePeriod")
    private String standardEffectivePeriod;

    @ApiModelProperty(value = "标准结论")
    @TableField("standardconClusion")
    private String standardconClusion;

    @ApiModelProperty(value = "不确定度")
    @TableField("uncertainty")
    private String uncertainty;

    @ApiModelProperty(value = "确认监测依据")
    @TableField("confirmMonitoringBasis")
    private String confirmMonitoringBasis;

    @ApiModelProperty(value = "参照检验依据")
    @TableField("reference")
    private String reference;

    @ApiModelProperty(value = "受理意见")
    @TableField("acceptOpinion")
    private String acceptOpinion;


    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType;
    }

    public String getDelegateUnitID() {
        return delegateUnitID;
    }

    public void setDelegateUnitID(String delegateUnitID) {
        this.delegateUnitID = delegateUnitID;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getDesignatedMonitoringBasis() {
        return designatedMonitoringBasis;
    }

    public void setDesignatedMonitoringBasis(String designatedMonitoringBasis) {
        this.designatedMonitoringBasis = designatedMonitoringBasis;
    }

    public String getDesignatedMonitoringItems() {
        return designatedMonitoringItems;
    }

    public void setDesignatedMonitoringItems(String designatedMonitoringItems) {
        this.designatedMonitoringItems = designatedMonitoringItems;
    }

    public String getDesignatedMonitoringRequirement() {
        return designatedMonitoringRequirement;
    }

    public void setDesignatedMonitoringRequirement(String designatedMonitoringRequirement) {
        this.designatedMonitoringRequirement = designatedMonitoringRequirement;
    }

    public String getAcceptanceReviewType() {
        return acceptanceReviewType;
    }

    public void setAcceptanceReviewType(String acceptanceReviewType) {
        this.acceptanceReviewType = acceptanceReviewType;
    }

    public String getPlannedCompletionTime() {
        return plannedCompletionTime;
    }

    public void setPlannedCompletionTime(String plannedCompletionTime) {
        this.plannedCompletionTime = plannedCompletionTime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getStandardEffectivePeriod() {
        return standardEffectivePeriod;
    }

    public void setStandardEffectivePeriod(String standardEffectivePeriod) {
        this.standardEffectivePeriod = standardEffectivePeriod;
    }

    public String getStandardconClusion() {
        return standardconClusion;
    }

    public void setStandardconClusion(String standardconClusion) {
        this.standardconClusion = standardconClusion;
    }

    public String getUncertainty() {
        return uncertainty;
    }

    public void setUncertainty(String uncertainty) {
        this.uncertainty = uncertainty;
    }

    public String getConfirmMonitoringBasis() {
        return confirmMonitoringBasis;
    }

    public void setConfirmMonitoringBasis(String confirmMonitoringBasis) {
        this.confirmMonitoringBasis = confirmMonitoringBasis;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAcceptOpinion() {
        return acceptOpinion;
    }

    public void setAcceptOpinion(String acceptOpinion) {
        this.acceptOpinion = acceptOpinion;
    }

    @Override
    public String toString() {
        return "QualityAssignmentStatement{" +
        "agreementNo=" + agreementNo +
        ", delegateType=" + delegateType +
        ", delegateUnitID=" + delegateUnitID +
        ", sampleID=" + sampleID +
        ", designatedMonitoringBasis=" + designatedMonitoringBasis +
        ", designatedMonitoringItems=" + designatedMonitoringItems +
        ", designatedMonitoringRequirement=" + designatedMonitoringRequirement +
        ", acceptanceReviewType=" + acceptanceReviewType +
        ", plannedCompletionTime=" + plannedCompletionTime +
        ", secret=" + secret +
        ", standardEffectivePeriod=" + standardEffectivePeriod +
        ", standardconClusion=" + standardconClusion +
        ", uncertainty=" + uncertainty +
        ", confirmMonitoringBasis=" + confirmMonitoringBasis +
        ", reference=" + reference +
        ", acceptOpinion=" + acceptOpinion +
        "}";
    }
}
