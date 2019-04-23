package com.quality.delegate.entity;

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
 * @since 2019-03-02
 */
@ApiModel(value="QualityReport对象", description="")
public class QualityReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "委托协议书编号")
    @TableField("agreementNo")
    private String agreementNo;
    @ApiModelProperty(value = "任务ID")
    @TableField("taskId")
    private String taskId;

    @ApiModelProperty(value = "样品ID")
    @TableField("sampleId")
    private String sampleId;
    @ApiModelProperty(value = "校验能力")
    @TableField("checkAbilityIDs")
    private String checkAbilityIDs;

    @ApiModelProperty(value = "名称")
    @TableField("reportName")
    private String reportName;

    @ApiModelProperty(value = "类型")
    @TableField("reportType")
    private String reportType;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "委托单位ID")
    @TableField("delegateUnitID")
    private String delegateUnitID;

    @ApiModelProperty(value = "委托单位名称")
    @TableField("unitName")
    private String unitName;


    @ApiModelProperty(value = "样品名称")
    @TableField("sampleName")
    private String sampleName;

    @ApiModelProperty(value = "任务签发人")
    @TableField("taskIssuedBy")
    private String taskIssuedBy;


    @ApiModelProperty(value = "样品编号")
    @TableField("sampleCode")
    private String sampleCode;

    @ApiModelProperty(value = "计算模板附件id")
    @TableField("calculateTemplateId")
    private String calculateTemplateId;

    @ApiModelProperty(value = "打印模板ID")
    @TableField("printTemplateId")
    private String printTemplateId;

    @ApiModelProperty(value = "生成模板的ID")
    @TableField("genReportId")
    private String genReportId;

    @ApiModelProperty(value = "计算模板名称")
    @TableField("calculateTemplateName")
    private String calculateTemplateName;

    @ApiModelProperty(value = "打印模板名称")
    @TableField("printTemplateName")
    private String printTemplateName;

    @ApiModelProperty(value = "生成模板名称")
    @TableField("genReportName")
    private String genReportName;

    public String getCalculateTemplateName() {
        return calculateTemplateName;
    }

    public void setCalculateTemplateName(String calculateTemplateName) {
        this.calculateTemplateName = calculateTemplateName;
    }

    public String getPrintTemplateName() {
        return printTemplateName;
    }

    public void setPrintTemplateName(String printTemplateName) {
        this.printTemplateName = printTemplateName;
    }

    public String getGenReportName() {
        return genReportName;
    }

    public void setGenReportName(String genReportName) {
        this.genReportName = genReportName;
    }

    public String getCalculateTemplateId() {
        return calculateTemplateId;
    }

    public void setCalculateTemplateId(String calculateTemplateId) {
        this.calculateTemplateId = calculateTemplateId;
    }

    public String getPrintTemplateId() {
        return printTemplateId;
    }

    public void setPrintTemplateId(String printTemplateId) {
        this.printTemplateId = printTemplateId;
    }

    public String getGenReportId() {
        return genReportId;
    }

    public void setGenReportId(String genReportId) {
        this.genReportId = genReportId;
    }

    public String getDelegateUnitID() {
        return delegateUnitID;
    }

    public void setDelegateUnitID(String delegateUnitID) {
        this.delegateUnitID = delegateUnitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getTaskIssuedBy() {
        return taskIssuedBy;
    }

    public void setTaskIssuedBy(String taskIssuedBy) {
        this.taskIssuedBy = taskIssuedBy;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
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

    @Override
    public String toString() {
        return "QualityReport{" +
        "agreementNo=" + agreementNo +
        ", taskId=" + taskId +
        ", sampleId=" + sampleId +
        ", checkAbilityIDs=" + checkAbilityIDs +
        ", reportName=" + reportName +
        ", reportType=" + reportType +
        ", remarks=" + remarks +
        "}";
    }
}
