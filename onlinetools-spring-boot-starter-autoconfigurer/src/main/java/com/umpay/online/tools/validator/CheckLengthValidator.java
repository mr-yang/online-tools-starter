package com.umpay.online.tools.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Auther: xiaoyang
 * @Date: 2019-07-31 10:58
 * @Description:限制字段长度的处理，会自动判断是中文还是字母,根据编码方式来确定汉字占几个字节
 */
public class CheckLengthValidator implements ConstraintValidator<CheckLength, String> {
    private String regx = "[^\\x00-\\xff]";
    private String message;
    private int minLength;
    private int maxLength;
    private String replacement;

    @Override
    public void initialize(CheckLength constraintAnnotation) {
        message = constraintAnnotation.message();
        minLength = constraintAnnotation.min();
        maxLength = constraintAnnotation.max();
        if (minLength < 0) {
            minLength = 0;
        }
        if (minLength > maxLength) {
            minLength = maxLength;
        }
        if (maxLength < 0) {
            maxLength = 1;
        }
        if (maxLength < minLength) {
            maxLength = minLength;
        }
        replacement = constraintAnnotation.encoding().getPlaceholder();
    }
    /**
     * 方法体内可以写自己需要校验的逻辑,返回值为false则校验不通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (StringUtils.isEmpty(value)) {
            if (minLength != 0) {
                isValid = false;
            }
        } else {
            String str = value.replaceAll(regx, replacement);
            if (str.length() < minLength || str.length() > maxLength) {
                isValid = false;
            }
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return isValid;
    }


}