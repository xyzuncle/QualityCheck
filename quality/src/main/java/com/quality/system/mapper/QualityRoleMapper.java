package com.quality.system.mapper;

import com.quality.system.entity.QualityRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quality.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
public interface QualityRoleMapper extends BaseMapper<QualityRole> {

    int insertRoleMenu(List<RoleMenu> list);

    List<Integer> selectByRoleId(@Param("roleID") String roleID);

    int  deleteByRoleId(@Param("roleID") String roleID);
}
