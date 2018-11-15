package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
@ApiModel(value="QualityRole对象", description="角色表")
public class QualityRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    @TableField("roleCode")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    @TableField("roleName")
    private String roleName;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "修改者")
    @TableField("updUser")
    private String updUser;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;


    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "QualityRole{" +
        "roleCode=" + roleCode +
        ", roleName=" + roleName +
        ", sort=" + sort +
        ", crtUser=" + crtUser +
        ", updUser=" + updUser +
        ", remarks=" + remarks +
        "}";
    }
}
