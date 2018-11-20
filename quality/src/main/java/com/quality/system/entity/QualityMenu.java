package com.quality.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
@ApiModel(value="QualityMenu对象", description="菜单表")
public class QualityMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "父级编号")
    @TableField("parentId")
    private String parentId;

    @ApiModelProperty(value = "所有父级编号")
    @TableField("parentIds")
    private String parentIds;

    @ApiModelProperty(value = "菜单编码")
    @TableField("menuCode")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menuName")
    private String menuName;

    @ApiModelProperty(value = "url链接")
    @TableField("menuUrl")
    private String menuUrl;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "增加权限")
    @TableField("addPermission")
    private String addPermission;

    @ApiModelProperty(value = "类型")
    @TableField("menuType")
    private Integer menuType;

    @ApiModelProperty(value = "状态")
    @TableField("menuState")
    private Integer menuState;

    @ApiModelProperty(value = "创建者")
    @TableField("crtUser")
    private String crtUser;

    @ApiModelProperty(value = "修改者")
    @TableField("updUser")
    private String updUser;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    public String getAddPermission() {
        return addPermission;
    }

    public void setAddPermission(String addPermission) {
        this.addPermission = addPermission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
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



    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
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


    @Override
    public String toString() {
        return "QualityMenu{" +
        "parentId=" + parentId +
        ", parentIds=" + parentIds +
        ", menuCode=" + menuCode +
        ", menuName=" + menuName +
        ", menuUrl=" + menuUrl +
        ", sort=" + sort +
        ", path=" + path +
        ", menuType=" + menuType +
        ", menuState=" + menuState +
        ", crtUser=" + crtUser +
        ", updUser=" + updUser +
        ", remarks=" + remarks +
        "}";
    }
}
