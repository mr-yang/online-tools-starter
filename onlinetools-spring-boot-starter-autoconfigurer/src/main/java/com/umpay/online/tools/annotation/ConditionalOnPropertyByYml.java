package com.umpay.online.tools.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

/**
 * @Description:
 * Spring Boot中@ConditionalOnProperty使用详解
 * 参考文章：https://www.cnblogs.com/secbro/p/12011522.html
 * @Author: tianxiaoyang
 * @Date: 2021/8/4 1:55 下午
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ConditionalOnProperty(prefix = "online.tools.property", name = "name",havingValue = "true")
public @interface ConditionalOnPropertyByYml {
}
