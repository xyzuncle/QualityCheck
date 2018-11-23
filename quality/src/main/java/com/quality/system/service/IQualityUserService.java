package com.quality.system.service;

import com.quality.system.entity.QualityUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */
public interface IQualityUserService extends IService<QualityUser>{

    /**
     * 根据用户ID和新密码，来比较密码是否正确
     * @param
     *        userId
     * @param
     *        pass
     * @return
     */
    public boolean compliePass(String userId,String pass);

    /**
     * 获取重复用户
     * @param loginName
     * @return
     */
    public boolean getExistUser(String loginName);

}
