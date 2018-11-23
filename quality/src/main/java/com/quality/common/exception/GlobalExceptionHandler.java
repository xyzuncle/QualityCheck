package com.quality.common.exception;



import com.alibaba.fastjson.JSON;
import com.quality.common.dto.ObjectRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * @Description: 全局异常处理类，用于controller类未捕获，或者抛出的异常
 *                可以处理 自定义异常，后台效验异常
 *                 无法处理Interceptor（拦截器）层7的异常，Spring 框架层的异常
 * @Author: 叶瑞
 * @CreateDate : 2018/11/22 22:29
 * @Version: 1.0
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 默认兜底的全局异常处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handleException(Exception e){
        LOGGER.error(e.getMessage(), e);
        return JSON.toJSON(new ObjectRestResponse().data("false").message("操作失败！").status(400));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object  badRequest(HttpServletRequest request,
                                                          ServletException ex) {
        String mothed = request.getMethod();
        return  JSON.toJSON(new ObjectRestResponse().data("false").message(mothed+"方法不能够支持").status(400));
    }

    /**
     * 自定义异常的处理
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    Object customException(Exception e){
        LOGGER.error(e.getMessage(), e);
        return JSON.toJSON(new ObjectRestResponse().data("false").message(e.getMessage()).status(500));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Object accessDeny(HttpServletRequest request, AccessDeniedException ex) {
        //ResponseEntity<Map<String, Object>>
        LOGGER.error(ex.getMessage(), ex);
        return JSON.toJSON(new ObjectRestResponse().data("false").message("没有权限访问").status(403));
    }


}
