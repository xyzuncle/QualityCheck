package com.quality.delegate.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by sunzw on 2019/3/3.
 */
public class QualityReportDto {

    private String id;

    private String taskIssuedBy;

    @JSONField(format = "yyyy-MM-dd")
    private Date taskIssuedDate;

    private String delegateUnit;

    private String sampleName;

    private String checkAbilityName;

    private String agreementNo;

    private String taskId;

    private String sampleId;

    private String checkAbilityIDs;

    private String reportName;

    private String reportType;

    private String remarks;

    public String getTaskIssuedBy() {
        return taskIssuedBy;
    }

    public void setTaskIssuedBy(String taskIssuedBy) {
        this.taskIssuedBy = taskIssuedBy;
    }

    public Date getTaskIssuedDate() {
        return taskIssuedDate;
    }

    public void setTaskIssuedDate(Date taskIssuedDate) {
        this.taskIssuedDate = taskIssuedDate;
    }

    public String getDelegateUnit() {
        return delegateUnit;
    }

    public void setDelegateUnit(String delegateUnit) {
        this.delegateUnit = delegateUnit;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getCheckAbilityName() {
        return checkAbilityName;
    }

    public void setCheckAbilityName(String checkAbilityName) {
        this.checkAbilityName = checkAbilityName;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getCheckAbilityIDs() {
        return checkAbilityIDs;
    }

    public void setCheckAbilityIDs(String checkAbilityIDs) {
        this.checkAbilityIDs = checkAbilityIDs;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
