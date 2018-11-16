package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
@ApiModel(value="QualityUser对象", description="用户表")
public class QualityUser extends BaseEntity implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属部门")
    @TableField("orgId")
    private String orgId;

    @ApiModelProperty(value = "姓名")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "登录名")
    @TableField("loginName")
    private String loginName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "修改者")
    @TableField("updUser")
    private String updUser;

    @ApiModelProperty(value = "是否可以登录")
    @TableField("loginFlag")
    private Integer loginFlag;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "机构名称")
    @TableField("orgName")
    private String orgName;

    @ApiModelProperty(value = "角色名称")
    @TableField("roleName")
    private String roleName;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 等权限设计好，这里完善，用于基于表达式的权限安全访问
         */
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public Integer getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(Integer loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "QualityUser{" +
        "orgId=" + orgId +
        ", userName=" + userName +
        ", loginName=" + loginName +
        ", password=" + password +
        ", salt=" + salt +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", email=" + email +
        ", sort=" + sort +
        ", crtUser=" + crtUser +
        ", updUser=" + updUser +
        ", loginFlag=" + loginFlag +
        ", remarks=" + remarks +
        ", orgName=" + orgName +
        ", roleName=" + roleName +
        "}";
    }
}
