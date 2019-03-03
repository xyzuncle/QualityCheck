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
