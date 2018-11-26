package com.quality.common.aop;


import com.quality.system.entity.QualityLogger;
import com.quality.system.entity.QualityUser;
import com.quality.system.service.impl.QualityLoggerServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by xyz on 2017/7/17.
 */

@Aspect
@Component
public class WebLogAspect {

    @Resource
    private QualityLoggerServiceImpl logService;

    //Service层切点,切点是自定义的annotation
    @Pointcut("@annotation(com.quality.common.aop.WebLogAction)")
    public void SysLogAspect() {

    }

    /**AspectJ支持5种类型的通知注解：

     -@Before 前置通知，在目标方法执行之前执行
     -@After：后置通知：在目标方法执行之后执行，无论是否发生异常
     -@AfterReturning:返回通知，在目标方法返回结果之后执行
     -@AfterThrowing：异常通知，在目标方法抛出异常之后通知。
     -@Around 环绕通知，围绕着目标方法执行。

     */

    @Before("SysLogAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            logService.save(getMthodRemark(joinPoint));
        } catch (Exception e) {
            //记录本地异常日志
            //logger.error("异常信息:{}", e.getMessage());
        }
    }

    public static QualityLogger getMthodRemark(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //通过security 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        QualityLogger aspectEntity = new QualityLogger();
        QualityUser user = (QualityUser)authentication.getPrincipal();
        aspectEntity.setLoginName(user.getLoginName());//登陆用户名
        aspectEntity.setUserName(user.getUserName()); //用户名
        aspectEntity.setIpAddress(getRemortIP(request));//ip地址
       // aspectEntity.setBrowser(request.getHeader("User-Agent"));//浏览器
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();   //获得参数列表
        StringBuffer parameters = new StringBuffer();
        for (int i = 0; i < arguments.length; i++) {
            parameters.append((i + 1) + ":" + (arguments[i]) + " ");
        }
        aspectEntity.setMethodName(methodName);//方法名
        //aspectEntity.setArguments(parameters == null ? "" : String.valueOf(parameters));//参数列表
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    WebLogAction methodCache = method.getAnnotation(WebLogAction.class);
                    aspectEntity.setOperation(methodCache.operation());//从注解上获取的操作内容
                    aspectEntity.setOperationType(methodCache.operation_type());//操作类型
                    aspectEntity.setMethodName(methodCache.moduleName());//模块名
                    break;
                }
            }
        }
        return aspectEntity;
    }

    public static String getRemortIP(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
