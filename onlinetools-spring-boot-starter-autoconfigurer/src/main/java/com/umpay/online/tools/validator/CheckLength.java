package com.umpay.online.tools.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description: 限制字段长度的注解, 改注解会判断字符串是否包含中文，
 * min最小长度，max最大长度，encoding编码方式，GBK一个汉字占2个字节，
 * UTF-8一个汉字占用3个字节，此注解的意义就是把数据存数据库的时候先判断一下。
 * 如果不需要区分数据是否有汉字可以用validator自带的Length注解
 * @Author: xiaoyang
 * @Date: 2019-08-01 17:26
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckLengthValidator.class)
@Documented
public @interface CheckLength {
    String message() default "字段长度不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 最小长度，默认0
     */
    int min() default 0;

    /**
     * 最大长度，默认1
     */
    int max() default 1;

    /**
     * 编码方式，默认GBK
     */
    EncodingEnum encoding() default EncodingEnum.GBK;

}
