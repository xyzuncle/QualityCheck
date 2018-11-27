package com.quality.system.service;

import com.quality.system.entity.QualityMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author yerui
 * @since 2018-11-21
 */
public interface IQualityMenuService extends IService<QualityMenu> {

    List<QualityMenu> selectListByParentId(Integer parentId);

    List<QualityMenu>  selectListByMenuType(List<String> MenuTypes);

    int selectByMenuId(Integer id);

    List<QualityMenu> selectListByRoleId(List<String> MenuTypes,String roleID);
}
