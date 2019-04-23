package com.quality.system.mapper;

import com.quality.system.entity.QualityUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
public interface QualityUserMapper extends BaseMapper<QualityUser> {

    Integer getExistUser(@Param("loginName") String loginName);

    int saveUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    int deleteUserRole(@Param("userId") String userId);

    List<Map<String,String>> queryByMap();
}
