package com.quality.system.service;

import com.quality.system.entity.QualityRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quality.system.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
public interface IQualityRoleService extends IService<QualityRole> {

    int insertRoleMenu(List<RoleMenu> list);

    List<Integer> selectByRoleId(String roleID);

    int deleteByRoleId(String roleID);
}
