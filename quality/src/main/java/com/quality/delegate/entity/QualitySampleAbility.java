package com.quality.delegate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author yerui
 * @since 2019-05-19
 */
@ApiModel(value="QualitySampleAbility对象", description="")
public class QualitySampleAbility {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "唯一标识",required = true)
    private String id;

    @TableField("checkAbilityId")
    private String checkAbilityId;

    @TableField("sampleId")
    private String sampleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckAbilityId() {
        return checkAbilityId;
    }

    public void setCheckAbilityId(String checkAbilityId) {
        this.checkAbilityId = checkAbilityId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return "QualitySampleAbility{" +
        "checkAbilityId=" + checkAbilityId +
        ", sampleId=" + sampleId +
        "}";
    }
}
