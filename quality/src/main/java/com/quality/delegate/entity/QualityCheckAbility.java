package com.quality.delegate.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 校准能力
 * </p>
 *
 * @author yerui
 * @since 2019-02-23
 */
@ApiModel(value="QualityCheckAbility对象", description="校准能力")
public class QualityCheckAbility extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规范名称")
    @TableField("specificationCName")
    private String specificationCName;

    @ApiModelProperty(value = "规范名称（英文）")
    @TableField("specificationEName")
    private String specificationEName;

    @ApiModelProperty(value = "代号")
    @TableField("codeCName")
    private String codeCName;

    @ApiModelProperty(value = "代号（英文）")
    @TableField("codeEName")
    private String codeEName;


    @ApiModelProperty(value = "所属类型")
    @TableField("abilityTypeValue")
    private String abilityTypeValue;



    @ApiModelProperty(value = "所属类型名称")
    @TableField("abilityTypeName")
    private String abilityTypeName;

    public String getCodeEName() {
        return codeEName;
    }

    public void setCodeEName(String codeEName) {
        this.codeEName = codeEName;
    }

    public String getSpecificationCName() {
        return specificationCName;
    }

    public void setSpecificationCName(String specificationCName) {
        this.specificationCName = specificationCName;
    }

    public String getSpecificationEName() {
        return specificationEName;
    }

    public void setSpecificationEName(String specificationEName) {
        this.specificationEName = specificationEName;
    }

    public String getCodeCName() {
        return codeCName;
    }

    public void setCodeCName(String codeCName) {
        this.codeCName = codeCName;
    }


    public String getAbilityTypeValue() {
        return abilityTypeValue;
    }

    public void setAbilityTypeValue(String abilityTypeValue) {
        this.abilityTypeValue = abilityTypeValue;
    }

    public String getAbilityTypeName() {
        return abilityTypeName;
    }

    public void setAbilityTypeName(String abilityTypeName) {
        this.abilityTypeName = abilityTypeName;
    }
}
