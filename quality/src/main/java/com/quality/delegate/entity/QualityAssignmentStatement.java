package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 委托协议书
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
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

    @ApiModelProperty(value = "参考规范ID集合")
    @TableField("referenceStandardIds")
    private String referenceStandardIds;

    @ApiModelProperty(value = "样机ID集合")
    @TableField("sampleIDs")
    private String sampleIDs;

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

    @ApiModelProperty(value = "流程定义id")
    @TableField("procdefId")
    private String procdefId;

    @ApiModelProperty(value = "执行实例ID")
    @TableField("executionId")
    private String executionId;

    @ApiModelProperty(value = "审批状态 0初始录入,1.开始审批,2为审批完成")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "附件")
    @TableField("agreementAttachment")
    private String agreementAttachment;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "受理人")
    @TableField("acceptor")
    private String acceptor;


    @ApiModelProperty(value = "受理时间")
    @TableField("acceptDate")
    private Date acceptDate;


    @ApiModelProperty(value = "复核人")
    @TableField("reviewer")
    private String reviewer;

    @ApiModelProperty(value = "复核时间")
    @TableField("reviewDate")
    private Date reviewDate;


    @ApiModelProperty(value = "派发人")
    @TableField("distributor")
    private String distributor;


    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

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

    public String getReferenceStandardIds() {
        return referenceStandardIds;
    }

    public void setReferenceStandardIds(String referenceStandardIds) {
        this.referenceStandardIds = referenceStandardIds;
    }

    public String getSampleIDs() {
        return sampleIDs;
    }

    public void setSampleIDs(String sampleIDs) {
        this.sampleIDs = sampleIDs;
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

    public String getProcdefId() {
        return procdefId;
    }

    public void setProcdefId(String procdefId) {
        this.procdefId = procdefId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAgreementAttachment() {
        return agreementAttachment;
    }

    public void setAgreementAttachment(String agreementAttachment) {
        this.agreementAttachment = agreementAttachment;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "QualityAssignmentStatement{" +
                "agreementNo='" + agreementNo + '\'' +
                ", delegateType='" + delegateType + '\'' +
                ", delegateUnitID='" + delegateUnitID + '\'' +
                ", referenceStandardIds='" + referenceStandardIds + '\'' +
                ", sampleIDs='" + sampleIDs + '\'' +
                ", designatedMonitoringBasis='" + designatedMonitoringBasis + '\'' +
                ", designatedMonitoringItems='" + designatedMonitoringItems + '\'' +
                ", designatedMonitoringRequirement='" + designatedMonitoringRequirement + '\'' +
                ", acceptanceReviewType='" + acceptanceReviewType + '\'' +
                ", plannedCompletionTime='" + plannedCompletionTime + '\'' +
                ", secret='" + secret + '\'' +
                ", standardEffectivePeriod='" + standardEffectivePeriod + '\'' +
                ", standardconClusion='" + standardconClusion + '\'' +
                ", uncertainty='" + uncertainty + '\'' +
                ", confirmMonitoringBasis='" + confirmMonitoringBasis + '\'' +
                ", reference='" + reference + '\'' +
                ", acceptOpinion='" + acceptOpinion + '\'' +
                ", procdefId='" + procdefId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", state=" + state +
                ", agreementAttachment='" + agreementAttachment + '\'' +
                ", remarks='" + remarks + '\'' +
                ", acceptor='" + acceptor + '\'' +
                ", acceptDate=" + acceptDate +
                ", reviewer='" + reviewer + '\'' +
                ", distributor='" + distributor + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
