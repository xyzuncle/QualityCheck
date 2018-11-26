package com.quality.common.aop;

import java.lang.annotation.Documented;

/**
 * Created by sunzw on 2018/11/13.
 */
@Documented
public @interface WebLogAction {

    String name() default "功能";

    String desc() default "操作";
}
