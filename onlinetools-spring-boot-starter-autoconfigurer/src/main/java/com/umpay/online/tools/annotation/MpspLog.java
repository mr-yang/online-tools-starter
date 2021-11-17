package com.umpay.online.tools.annotation;


import java.lang.annotation.*;

/**
 * 需要打简要日志的类添加此注解
 * @author tianxiaoyang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MpspLog {
}
