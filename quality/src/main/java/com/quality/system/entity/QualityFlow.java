package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 流程定义关系表，用于维护前端选值和流程定义的 关联关系
 * </p>
 *
 * @author yerui
 * @since 2019-01-04
 */
@ApiModel(value="QualityFlow对象", description="流程定义关系表，用于维护前端选值和流程定义的 关联关系")
public class QualityFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程定义id")
    @TableField("processId")
    private String processId;

    @ApiModelProperty(value = "任务id")
    @TableField("taskId")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    @TableField("taskName")
    private String taskName;

    @ApiModelProperty(value = "任务表达式")
    @TableField("taskExpression")
    private String taskExpression;

    @ApiModelProperty(value = "任务表达式的值")
    @TableField("taskValue")
    private String taskValue;

    @ApiModelProperty(value = "1 任务 2 流水")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "流水名称")
    @TableField("seqName")
    private String seqName;

    @ApiModelProperty(value = "流水ID")
    @TableField("seqId")
    private String seqId;

    @ApiModelProperty(value = "流水表达式")
    @TableField("seqExpression")
    private String seqExpression;

    @ApiModelProperty(value = "流水表达式的值")
    @TableField("seqValue")
    private String seqValue;


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskExpression() {
        return taskExpression;
    }

    public void setTaskExpression(String taskExpression) {
        this.taskExpression = taskExpression;
    }

    public String getTaskValue() {
        return taskValue;
    }

    public void setTaskValue(String taskValue) {
        this.taskValue = taskValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSeqExpression() {
        return seqExpression;
    }

    public void setSeqExpression(String seqExpression) {
        this.seqExpression = seqExpression;
    }

    public String getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(String seqValue) {
        this.seqValue = seqValue;
    }

    @Override
    public String toString() {
        return "QualityFlow{" +
        "processId=" + processId +
        ", taskId=" + taskId +
        ", taskName=" + taskName +
        ", taskExpression=" + taskExpression +
        ", taskValue=" + taskValue +
        ", type=" + type +
        ", seqName=" + seqName +
        ", seqId=" + seqId +
        ", seqExpression=" + seqExpression +
        ", seqValue=" + seqValue +
        "}";
    }
}
