package com.quality.store.service.impl;

import com.quality.store.entity.QualityFastdfsIndex;
import com.quality.store.mapper.QualityFastdfsIndexMapper;
import com.quality.store.service.IQualityFastdfsIndexService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用于记录fastfds的索引记录表，方便查询是否重复写入数据，或者删除冗余数据 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-30
 */
@Service
public class QualityFastdfsIndexServiceImpl extends ServiceImpl<QualityFastdfsIndexMapper, QualityFastdfsIndex> implements IQualityFastdfsIndexService {

}
