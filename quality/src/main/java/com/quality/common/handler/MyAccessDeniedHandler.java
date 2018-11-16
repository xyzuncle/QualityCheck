package com.quality.common.handler;

import com.alibaba.fastjson.JSON;
import com.quality.common.exception.BaseException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的handel
 */

public class MyAccessDeniedHandler implements AuthenticationFailureHandler {

    private String errorPage;

    public MyAccessDeniedHandler() {
    }

    public MyAccessDeniedHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            String result = JSON.toJSONString(new BaseException("用户名或密码错误或者用户被禁用",500));
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result);
    }



}
