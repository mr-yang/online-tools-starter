package com.umpay.online.tools.annotation;

import java.lang.annotation.*;

/**
 * 不需要打简要日志的方法添加此注解
 * @author tianxiaoyang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ExcludeMpspLog {
}
