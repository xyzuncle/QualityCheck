package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author yerui
 * @since 2018-11-26
 */
@ApiModel(value="QualityLogger对象", description="日志表")
public class QualityLogger extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "登录名")
    @TableField("loginName")
    private String loginName;

    @ApiModelProperty(value = "IP地址")
    @TableField("ipAddress")
    private String ipAddress;

    @ApiModelProperty(value = "操作")
    @TableField("operation")
    private String operation;

    @ApiModelProperty(value = "操作时间")
    @TableField("optTime")
    private String optTime;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @TableField(exist = false)
    private long requestTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "QualityLogger{" +
        "userName=" + userName +
        ", loginName=" + loginName +
        ", ipAddress=" + ipAddress +
        ", operation=" + operation +
        ", optTime=" + optTime +
        ", remarks=" + remarks +
        "}";
    }
}
