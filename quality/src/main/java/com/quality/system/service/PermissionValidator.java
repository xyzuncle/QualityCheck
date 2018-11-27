package com.quality.system.service;

import com.quality.system.entity.QualityMenu;
import com.quality.system.entity.QualityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component("permissionValidator")
public class PermissionValidator {

    //注入servie
    @Autowired
    private IQualityMenuService menuService;

    public boolean hasSomePermission(HttpServletRequest request){
        String url = request.getRequestURI();
        System.out.println(url);

        //查询当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        QualityUser user = (QualityUser)authentication.getPrincipal();

        List<String> MenuTypes = new ArrayList<String>(){{add("0"); add("1");add("2");}};
        List<QualityMenu> list = menuService.selectListByRoleId(MenuTypes,user.getRoleId());
        //urls 存当前用户所有的路径
        List<String> urls = new ArrayList<>();
        list.forEach(item->{
                urls.add(item.getMenuUrl());
        });

        if(urls.contains(url)){
            return true;
        }else{
            return false;
        }

    }
}
