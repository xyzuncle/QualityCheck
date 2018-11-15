package com.quality.system.service.impl;

import com.quality.system.entity.QualityUser;
import com.quality.system.mapper.QualityUserMapper;
import com.quality.system.service.IQualityUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
@Service
public class QualityUserServiceImpl extends ServiceImpl<QualityUserMapper, QualityUser> implements IQualityUserService {

}
