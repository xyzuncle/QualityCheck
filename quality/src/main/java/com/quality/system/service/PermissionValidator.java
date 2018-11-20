package com.quality.system.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("permissionValidator")
public class PermissionValidator {

    //注入servie

    public boolean hasSomePermission(HttpServletRequest request){
        String url = request.getRequestURI();
        System.out.println(url);

        //for each
        return true;
    }
}
