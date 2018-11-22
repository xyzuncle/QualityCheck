package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.quality.common.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author yerui
 * @since 2018-11-21
 */
@ApiModel(value="QualityMenu对象", description="菜单表")
public class QualityMenu extends SuperEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级编号")
    @TableField("parentId")
    private Integer parentId;

    @ApiModelProperty(value = "所有父级编号")
    @TableField("parentIds")
    private String parentIds;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menuName")
    private String menuName;

    @ApiModelProperty(value = "url链接")
    @TableField("menuUrl")
    private String menuUrl;

    @ApiModelProperty(value = "菜单图标")
    @TableField("menuIcon")
    private String menuIcon;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "类型")
    @TableField("menuType")
    private String menuType;

    @ApiModelProperty(value = "状态")
    @TableField("menuState")
    private String menuState;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "修改者")
    @TableField("updUser")
    private String updUser;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "增加权限")
    @TableField("addPermission")
    private String addPermission;

    @ApiModelProperty(value = "编辑权限")
    @TableField("editPermission")
    private String editPermission;

    @ApiModelProperty(value = "删除权限")
    @TableField("deletePermission")
    private String deletePermission;

    @ApiModelProperty(value = "查询权限")
    @TableField("queryPermission")
    private String queryPermission;


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
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

    public String getAddPermission() {
        return addPermission;
    }

    public void setAddPermission(String addPermission) {
        this.addPermission = addPermission;
    }

    public String getEditPermission() {
        return editPermission;
    }

    public void setEditPermission(String editPermission) {
        this.editPermission = editPermission;
    }

    public String getDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(String deletePermission) {
        this.deletePermission = deletePermission;
    }

    public String getQueryPermission() {
        return queryPermission;
    }

    public void setQueryPermission(String queryPermission) {
        this.queryPermission = queryPermission;
    }



    @Override
    public String toString() {
        return "QualityMenu{" +
        "parentId=" + parentId +
        ", parentIds=" + parentIds +
        ", menuName=" + menuName +
        ", menuUrl=" + menuUrl +
        ", menuIcon=" + menuIcon +
        ", sort=" + sort +
        ", path=" + path +
        ", menuType=" + menuType +
        ", menuState=" + menuState +
        ", crtUser=" + crtUser +
        ", updUser=" + updUser +
        ", remarks=" + remarks +
        ", addPermission=" + addPermission +
        ", editPermission=" + editPermission +
        ", deletePermission=" + deletePermission +
        ", queryPermission=" + queryPermission +
        "}";
    }
}
