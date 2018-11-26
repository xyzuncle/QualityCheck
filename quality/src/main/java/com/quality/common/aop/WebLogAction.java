package com.quality.common.aop;

import java.lang.annotation.*;

/**
 * Created by xyz on 2017/7/17.
 * 自定义AOP的注解，针对使用该注解的时候方法，进行AOP操作
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLogAction {
    //模块名称
    String moduleName() default "";
    //操作类型 是删除 ，增加 查询，修改
    String operation_type() default "查询";
    //操作内容
    String operation() default "";
}