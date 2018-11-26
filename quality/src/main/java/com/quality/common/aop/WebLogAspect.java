package com.quality.common.aop;

import com.alibaba.fastjson.JSON;
import com.quality.system.entity.QualityLogger;
import com.quality.system.entity.QualityUser;
import com.quality.system.service.IQualityLoggerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by sunzw on 2018/11/13.
 */
@Aspect
@Component
public class WebLogAspect {

    private static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private ThreadLocal<QualityLogger> tlocal = new ThreadLocal<QualityLogger>();

    @Autowired
    private IQualityLoggerService  logService;

    @Pointcut("@annotation(com.quality.common.aop.WebLogAction)")
    public void webRequestLog() {}

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {

            long beginTime = System.currentTimeMillis();
            // 接收到请求，记录请求内容
           ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
         /*    String beanName = joinPoint.getSignature().getDeclaringTypeName();
            String method = request.getMethod();*/
            String remoteAddr = getIpAddr(request);

            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Method targetMethod = methodSignature.getMethod();
            Class clazz = targetMethod.getClass();
            WebLogAction logAction = (WebLogAction)clazz.getAnnotation(WebLogAction.class);
            QualityLogger optLog = new QualityLogger();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object object = authentication.getPrincipal();
                if (object != null) {
                    QualityUser curUser = (QualityUser) object;
                    optLog.setLoginName(curUser.getLoginName());
                    optLog.setUserName(curUser.getUserName());
                }
            }
            optLog.setIpAddress(remoteAddr);
            optLog.setRequestTime(beginTime);
            //optLog.setOperation(logAction.name()+"—"+logAction.desc());
            tlocal.set(optLog);

        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }
    }


    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，返回内容
            QualityLogger optLog = tlocal.get();
            long beginTime = optLog.getRequestTime();
            long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
            optLog.setOptTime(Long.toString(requestTime)+"s");
            logService.save(optLog);
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
        }
    }


    /**
     * 获取登录用户远程主机ip地址
     *
     * @param request
     * @return
     */
     public  String getIpAddr(HttpServletRequest request) {
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

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                Object jsonObj = JSON.toJSON(paramsArray[i]);
                params += jsonObj.toString() + " ";
            }
        }
        return params.trim();
    }
}
