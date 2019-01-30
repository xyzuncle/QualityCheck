package com.quality.store.mapper;

import com.quality.store.entity.QualityFastdfsIndex;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用于记录fastfds的索引记录表，方便查询是否重复写入数据，或者删除冗余数据 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2019-01-30
 */
public interface QualityFastdfsIndexMapper extends BaseMapper<QualityFastdfsIndex> {

}
