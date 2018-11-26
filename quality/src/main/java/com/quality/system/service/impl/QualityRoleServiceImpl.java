package com.quality.system.service.impl;

import com.quality.system.entity.QualityRole;
import com.quality.system.entity.RoleMenu;
import com.quality.system.mapper.QualityRoleMapper;
import com.quality.system.service.IQualityRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
@Service
public class QualityRoleServiceImpl extends ServiceImpl<QualityRoleMapper, QualityRole> implements IQualityRoleService {

    @Override
    public int insertRoleMenu(List<RoleMenu> list) {
        return this.baseMapper.insertRoleMenu(list);
    }

    @Override
    public List<Integer> selectByRoleId(String roleID) {
        return this.baseMapper.selectByRoleId(roleID);
    }

    @Override
    public int deleteByRoleId(String roleID) {
        return this.baseMapper.deleteByRoleId(roleID);
    }
}
