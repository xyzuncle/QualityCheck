package com.quality.system.mapper;

import com.quality.system.entity.QualityMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-11-21
 */
public interface QualityMenuMapper extends BaseMapper<QualityMenu> {


    List<QualityMenu>  selectListByParentId(@Param("parentId")Integer parentId);

    List<QualityMenu>  selectListByMenuType(List<String> MenuTypes);

    int selectByMenuId(Integer id);

}
