package com.quality.delegate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 委托单位
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@TableName("quality_delegateUnit")
@ApiModel(value="QualityDelegateunit对象", description="委托单位")
public class QualityDelegateunit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位名称")
    @TableField("unitName")
    private String unitName;


    @ApiModelProperty(value = "单位编码")
    @TableField("unitCode")
    private String unitCode;


    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "邮编")
    @TableField("zipCode")
    private String zipCode;

    @ApiModelProperty(value = "联系人")
    @TableField("linkMan")
    private String linkMan;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "手机")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "传真")
    @TableField("fax")
    private String fax;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    @Override
    public String toString() {
        return "QualityDelegateunit{" +
        "unitName=" + unitName +
        ", address=" + address +
        ", zipCode=" + zipCode +
        ", linkMan=" + linkMan +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", fax=" + fax +
        "}";
    }
}
