package com.quality.common.handler;

import com.alibaba.fastjson.JSON;

import com.quality.common.aop.WebLogAction;
import com.quality.common.dto.ObjectRestResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 成功登出后需要执行的行为
 */

public class LoginOutHandler implements LogoutSuccessHandler {
    @Override
    @WebLogAction(moduleName = "退出登录",operation = "用户退出系统",operation_type = "退出登录")
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
      /*  String userName = authentication.getName().toString();*/
        String result = JSON.toJSONString(new ObjectRestResponse().data("true").message("用户退出！"));
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);
    }
}
