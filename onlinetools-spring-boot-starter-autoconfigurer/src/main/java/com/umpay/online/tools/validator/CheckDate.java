package com.umpay.online.tools.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @Description: 检查日期格式是否正确，
 * 并检查上传的日期是否大于或小于当前日期
 * @Author: xiaoyang
 * @Date: 2019-08-28 09:46
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface CheckDate {
    String message() default "日期格式不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    /**
     * 日期字符串格式，默认yyyyMMdd,例如：20190828
     */
    DateEnum pattern() default DateEnum.YYYYMMDD;

    /**
     * 是否能为空，默认可以
     */
    boolean empty() default true;

    /**
     * 参数日期和当前时间做比较，等于、大于等于，小于登录，默认小于等于
     */
    OperatorEnum operator() default  OperatorEnum.LTE;

}
