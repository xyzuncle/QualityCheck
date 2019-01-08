package com.quality.system.service.impl;

import com.quality.system.entity.QualityFlow;
import com.quality.system.mapper.QualityFlowMapper;
import com.quality.system.service.IQualityFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程定义关系表，用于维护前端选值和流程定义的 关联关系 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-01-04
 */
@Service
public class QualityFlowServiceImpl extends ServiceImpl<QualityFlowMapper, QualityFlow> implements IQualityFlowService {

}
