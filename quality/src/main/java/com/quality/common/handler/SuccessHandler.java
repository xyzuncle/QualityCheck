package com.quality.common.handler;


import com.alibaba.fastjson.JSON;
import com.quality.common.aop.WebLogAction;
import com.quality.common.dto.LoginResult;
import com.quality.common.dto.ObjectRestResponse;
import com.quality.system.entity.QualityUser;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {



    private String forwardUrl;



    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    public SuccessHandler(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    @WebLogAction(moduleName = "登录验证",operation = "通过首页登录",operation_type = "登录")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {




        QualityUser baseUser = (QualityUser) authentication.getPrincipal();
        LoginResult loginResult = new LoginResult();
        loginResult.setName(baseUser.getLoginName());
        String result = JSON.toJSONString(new ObjectRestResponse().data(loginResult).message("登录成功！"));
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);
        logger.info("登录成功！！");
    }


}
