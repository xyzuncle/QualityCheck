package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 认可标准
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */
@ApiModel(value="QualityReferenceStandard对象", description="认可标准")
public class QualityReferenceStandard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "测量仪器名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "被测量")
    @TableField("measured")
    private String measured;

    @ApiModelProperty(value = "标准规范")
    @TableField("standardSpecification")
    private String standardSpecification;

    @ApiModelProperty(value = "测量范围")
    @TableField("measureRange")
    private String measureRange;

    @ApiModelProperty(value = "扩展不确定度")
    @TableField("extend")
    private String extend;

    @ApiModelProperty(value = "说明")
    @TableField("remark")
    private String remark;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasured() {
        return measured;
    }

    public void setMeasured(String measured) {
        this.measured = measured;
    }

    public String getStandardSpecification() {
        return standardSpecification;
    }

    public void setStandardSpecification(String standardSpecification) {
        this.standardSpecification = standardSpecification;
    }

    public String getMeasureRange() {
        return measureRange;
    }

    public void setMeasureRange(String measureRange) {
        this.measureRange = measureRange;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "QualityReferenceStandard{" +
        "name=" + name +
        ", measured=" + measured +
        ", standardSpecification=" + standardSpecification +
        ", measureRange=" + measureRange +
        ", extend=" + extend +
        ", remark=" + remark +
        "}";
    }
}
