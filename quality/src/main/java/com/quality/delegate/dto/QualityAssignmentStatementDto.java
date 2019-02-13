package com.quality.delegate.dto;

import com.quality.delegate.entity.QualityReferenceStandard;
import com.quality.delegate.entity.QualitySample;

import java.util.List;

/**
 * Created by sunzw on 2019/1/28.
 */
public class QualityAssignmentStatementDto {


    private Integer state=0;// 状态 0初始录入,1.开始审批,2为审批完成

    private String assignmentId;

    private String agreementNo;

    private String delegateType;

    private String delegateUnitID;

    private String sampleID;

    private String designatedMonitoringBasis;

    private String designatedMonitoringItems;

    private String designatedMonitoringRequirement;

    private String acceptanceReviewType;

    private String plannedCompletionTime;

    private String secret;

    private String standardEffectivePeriod;

    private String standardconClusion;

    private String uncertainty;

    private String confirmMonitoringBasis;

    private String reference;

    private String acceptOpinion;

    private List<QualitySample> qualitySamples;

    private List<QualityReferenceStandard> qualityReferenceStandards;


    private String unitId;

    private String unitName;

    private String address;

    private String zipCode;

    private String linkMan;

    private String phone;

    private String mobile;

    private String fax;

    public String getAssignmentId() {
        return assignmentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
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

    public List<QualitySample> getQualitySamples() {
        return qualitySamples;
    }

    public void setQualitySamples(List<QualitySample> qualitySamples) {
        this.qualitySamples = qualitySamples;
    }

    public List<QualityReferenceStandard> getQualityReferenceStandards() {
        return qualityReferenceStandards;
    }

    public void setQualityReferenceStandards(List<QualityReferenceStandard> qualityReferenceStandards) {
        this.qualityReferenceStandards = qualityReferenceStandards;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
