package com.quality.system.mapper;

import com.quality.system.entity.QualityUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

}
