package com.quality.system.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author yerui
 * @since 2018-11-28
 */
@ApiModel(value="QualityDictionaries对象", description="字典表")
public class QualityDictionaries extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("dictName")
    private String dictName;

    @ApiModelProperty(value = "值")
    @TableField("dictValue")
    private String dictValue;

    @ApiModelProperty(value = "类型")
    @TableField("dictType")
    private String dictType;

    @ApiModelProperty(value = "父级编号")
    @TableField("parentId")
    private String parentId;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "QualityDictionaries{" +
        "dictName=" + dictName +
        ", dictValue=" + dictValue +
        ", dictType=" + dictType +
        ", parentId=" + parentId +
        ", sort=" + sort +
        ", remark=" + remark +
        "}";
    }
}
