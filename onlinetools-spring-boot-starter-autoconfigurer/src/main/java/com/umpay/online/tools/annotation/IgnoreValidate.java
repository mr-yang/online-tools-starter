package com.umpay.online.tools.annotation;

import java.lang.annotation.*;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-11 14:10
 * @Description:忽略接口字段校验注解，添加到类上面
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface  IgnoreValidate {
}
