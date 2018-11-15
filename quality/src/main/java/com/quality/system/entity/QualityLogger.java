package com.quality.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.quality.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2018-11-13
 */
@ApiModel(value="QualityLogger对象", description="日志表")
public class QualityLogger extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求地址")
    @TableField("remoteAddr")
    private String remoteAddr;

    @ApiModelProperty(value = "请求方式")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "请求类")
    @TableField("beanName")
    private String beanName;

    @ApiModelProperty(value = "请求路径")
    @TableField("requestUri")
    private String requestUri;

    @ApiModelProperty(value = "请求类方法")
    @TableField("signature")
    private String signature;

    @ApiModelProperty(value = "请求类方法参数")
    @TableField("args")
    private String args;

    @ApiModelProperty(value = "响应时间")
    @TableField("requestTime")
    private Long requestTime;

    @ApiModelProperty(value = "结果")
    @TableField("result")
    private String result;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;


    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "QualityLogger{" +
        "remoteAddr=" + remoteAddr +
        ", method=" + method +
        ", beanName=" + beanName +
        ", requestUri=" + requestUri +
        ", signature=" + signature +
        ", args=" + args +
        ", requestTime=" + requestTime +
        ", result=" + result +
        ", crtUser=" + crtUser +
        ", remarks=" + remarks +
        "}";
    }
}
