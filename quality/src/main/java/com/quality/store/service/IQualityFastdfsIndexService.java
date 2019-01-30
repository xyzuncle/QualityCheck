package com.quality.store.service;

import com.quality.store.entity.QualityFastdfsIndex;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用于记录fastfds的索引记录表，方便查询是否重复写入数据，或者删除冗余数据 服务类
 * </p>
 *
 * @author yerui
 * @since 2019-01-30
 */
public interface IQualityFastdfsIndexService extends IService<QualityFastdfsIndex> {

}
