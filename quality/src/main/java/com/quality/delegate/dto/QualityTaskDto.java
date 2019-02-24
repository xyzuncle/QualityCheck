package com.quality.delegate.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.quality.delegate.entity.QualityCheckAbility;
import com.quality.delegate.entity.QualitySample;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by sunzw on 2019/2/24.
 */
public class QualityTaskDto {

    private String taskId;

    private String taskIssuedBy;

    private LocalDate taskIssuedDate;

    private String delegateUnit;

    private String sampleIDs;

    private String projectLeader;

    private String projectParticipant;

    private String projectReviewer;

    private String environmentalTest;

    private String otherTest;

    private String checkAbilityIDs;

    private LocalDate plannedCompletionDate;

    private String standardValidityDate;

    private String remarks;


    private String agreementNo;

    private String delegateType;


    private List<QualitySample> qualitySamples;

    private List<QualityCheckAbility> qualityCheckAbilities;




    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskIssuedBy() {
        return taskIssuedBy;
    }

    public void setTaskIssuedBy(String taskIssuedBy) {
        this.taskIssuedBy = taskIssuedBy;
    }

    public LocalDate getTaskIssuedDate() {
        return taskIssuedDate;
    }

    public void setTaskIssuedDate(LocalDate taskIssuedDate) {
        this.taskIssuedDate = taskIssuedDate;
    }

    public String getDelegateUnit() {
        return delegateUnit;
    }

    public void setDelegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
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

    public String getSampleIDs() {
        return sampleIDs;
    }

    public void setSampleIDs(String sampleIDs) {
        this.sampleIDs = sampleIDs;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectParticipant() {
        return projectParticipant;
    }

    public void setProjectParticipant(String projectParticipant) {
        this.projectParticipant = projectParticipant;
    }

    public String getProjectReviewer() {
        return projectReviewer;
    }

    public void setProjectReviewer(String projectReviewer) {
        this.projectReviewer = projectReviewer;
    }

    public String getEnvironmentalTest() {
        return environmentalTest;
    }

    public void setEnvironmentalTest(String environmentalTest) {
        this.environmentalTest = environmentalTest;
    }

    public String getOtherTest() {
        return otherTest;
    }

    public void setOtherTest(String otherTest) {
        this.otherTest = otherTest;
    }

    public String getCheckAbilityIDs() {
        return checkAbilityIDs;
    }

    public void setCheckAbilityIDs(String checkAbilityIDs) {
        this.checkAbilityIDs = checkAbilityIDs;
    }

    public LocalDate getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    public void setPlannedCompletionDate(LocalDate plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public String getStandardValidityDate() {
        return standardValidityDate;
    }

    public void setStandardValidityDate(String standardValidityDate) {
        this.standardValidityDate = standardValidityDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<QualitySample> getQualitySamples() {
        return qualitySamples;
    }

    public void setQualitySamples(List<QualitySample> qualitySamples) {
        this.qualitySamples = qualitySamples;
    }

    public List<QualityCheckAbility> getQualityCheckAbilities() {
        return qualityCheckAbilities;
    }

    public void setQualityCheckAbilities(List<QualityCheckAbility> qualityCheckAbilities) {
        this.qualityCheckAbilities = qualityCheckAbilities;
    }

}
