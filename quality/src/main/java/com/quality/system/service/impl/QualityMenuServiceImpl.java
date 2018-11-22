package com.quality.system.service.impl;

import com.quality.system.entity.QualityMenu;
import com.quality.system.mapper.QualityMenuMapper;
import com.quality.system.service.IQualityMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-11-21
 */
@Service
public class QualityMenuServiceImpl extends ServiceImpl<QualityMenuMapper, QualityMenu> implements IQualityMenuService {

    @Override
    public List<QualityMenu> selectListByParentId(Integer parentId) {
        return this.baseMapper.selectListByParentId(parentId);
    }
}
