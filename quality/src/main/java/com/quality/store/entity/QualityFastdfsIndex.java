package com.quality.store.entity;

import com.quality.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用于记录fastfds的索引记录表，方便查询是否重复写入数据，或者删除冗余数据
 * </p>
 *
 * @author yerui
 * @since 2019-01-30
 */
@ApiModel(value="QualityFastdfsIndex对象", description="用于记录fastfds的索引记录表，方便查询是否重复写入数据，或者删除冗余数据")
public class QualityFastdfsIndex extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "fastdfs文件索引")
    @TableField("fastdfsIndex")
    private String fastdfsIndex;

    @ApiModelProperty(value = "当前索引的文件的业务id")
    @TableField("businessId")
    private String businessId;

    @ApiModelProperty(value = "生成的条形码编号")
    @TableField("barcode")
    private String barcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getFastdfsIndex() {
        return fastdfsIndex;
    }

    public void setFastdfsIndex(String fastdfsIndex) {
        this.fastdfsIndex = fastdfsIndex;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "QualityFastdfsIndex{" +
        "fastdfsIndex=" + fastdfsIndex +
        ", businessId=" + businessId +
        "}";
    }
}
