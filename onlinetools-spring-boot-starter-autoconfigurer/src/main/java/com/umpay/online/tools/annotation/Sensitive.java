package com.umpay.online.tools.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.umpay.online.tools.enums.SensitiveStrategy;
import com.umpay.online.tools.http.SensitiveJsonSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tianxiaoyang
 * @date 2021-11-15 13:40
 * @describe 脱敏处理
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive {
    /**
     * 脱敏策略
     * @return
     */
    SensitiveStrategy strategy();


}
