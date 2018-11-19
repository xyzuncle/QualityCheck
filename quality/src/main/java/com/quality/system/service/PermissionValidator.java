package com.quality.system.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("permissionValidator")
public class PermissionValidator {

    public boolean hasSomePermission(HttpServletRequest request){
        String url = request.getRequestURI();
        System.out.println(url);
        return true;
    }
}
