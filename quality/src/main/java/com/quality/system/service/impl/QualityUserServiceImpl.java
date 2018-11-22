package com.quality.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.exception.BaseException;
import com.quality.common.util.MD5;
import com.quality.system.entity.QualityUser;
import com.quality.system.mapper.QualityUserMapper;
import com.quality.system.service.IQualityUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class QualityUserServiceImpl extends ServiceImpl<QualityUserMapper, QualityUser> implements IQualityUserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        QualityUser user = null;
        //因为需求有变动，需要前端配合请求，约定成俗，根据不同的方式来进行登录验证
        if(StringUtils.isNotBlank(username)){
            QueryWrapper<QualityUser> qw = new QueryWrapper<>();
            qw.eq("loginName", username);
            user = this.getOne(qw);
        }

        //如果查不到用户，抛出异常
        if(user == null){
            throw new BaseException("用户不存在",500);
        }
        if(user!=null){
                if(user.isEnabled()==false){
                    throw new BaseException("该账户被禁用无法登陆",500);
                }
        }
        //返回当前唯一的用户
        return user;

    }

    @Override
    public boolean compliePass(String userId, String pass) {
        QualityUser QualityUser= this.baseMapper.selectById(userId);
        String oldPass = QualityUser.getPassword();
        //考虑到资源卫星老用户的修改密码情况，这里需要做特别处理
        boolean result = MD5.compilePass(pass, oldPass);





        return result;
    }
}
