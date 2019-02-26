package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 任务单
 * </p>
 *
 * @author yerui
 * @since 2019-02-24
 */
@ApiModel(value="QualityTask对象", description="任务单")
public class QualityTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务签发人")
    @TableField("taskIssuedBy")
    private String taskIssuedBy;

    @ApiModelProperty(value = "任务签发日期")
    @TableField("taskIssuedDate")
    private LocalDate taskIssuedDate;

    @ApiModelProperty(value = "委托单位")
    @TableField("delegateUnit")
    private String delegateUnit;

    @ApiModelProperty(value = "样品ID集合")
    @TableField("sampleIDs")
    private String sampleIDs;

    @ApiModelProperty(value = "项目负责人")
    @TableField("projectLeader")
    private String projectLeader;

    @ApiModelProperty(value = "项目参加人")
    @TableField("projectParticipant")
    private String projectParticipant;

    @ApiModelProperty(value = "项目复核人")
    @TableField("projectReviewer")
    private String projectReviewer;

    @ApiModelProperty(value = "环境试验")
    @TableField("environmentalTest")
    private String environmentalTest;

    @ApiModelProperty(value = "其他试验")
    @TableField("otherTest")
    private String otherTest;

    @ApiModelProperty(value = "校准能力ID集合")
    @TableField("checkAbilityIDs")
    private String checkAbilityIDs;

    @ApiModelProperty(value = "计划完成日期")
    @TableField("plannedCompletionDate")
    private LocalDate plannedCompletionDate;

    @ApiModelProperty(value = "标准有效期")
    @TableField("standardValidityDate")
    private String standardValidityDate;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "协议书编号")
    @TableField("agreementNo")
    private String agreementNo;

    @ApiModelProperty(value = "委托类型")
    @TableField("delegateType")
    private String delegateType;


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

    @Override
    public String toString() {
        return "QualityTask{" +
                "taskIssuedBy=" + taskIssuedBy +
                ", taskIssuedDate='" + taskIssuedDate + '\'' +
                ", delegateUnit='" + delegateUnit + '\'' +
                ", sampleIDs='" + sampleIDs + '\'' +
                ", projectLeader='" + projectLeader + '\'' +
                ", projectParticipant='" + projectParticipant + '\'' +
                ", projectReviewer='" + projectReviewer + '\'' +
                ", environmentalTest='" + environmentalTest + '\'' +
                ", otherTest='" + otherTest + '\'' +
                ", checkAbilityIDs='" + checkAbilityIDs + '\'' +
                ", plannedCompletionDate=" + plannedCompletionDate +
                ", standardValidityDate='" + standardValidityDate + '\'' +
                ", remarks='" + remarks + '\'' +
                ", agreementNo='" + agreementNo + '\'' +
                ", delegateType='" + delegateType + '\'' +
                '}';
    }
}
