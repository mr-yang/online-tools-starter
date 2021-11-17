package com.umpay.online.tools.util;


import com.umpay.online.tools.aspect.ValidateBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Auther: xiaoyang
 * @Date: 2020-05-11 10:51
 * @Description:
 */
public class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

//    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//            .configure()
//            // 快速失败模式
//            .failFast(true)
//            .buildValidatorFactory();
    /**
     * 校验bean
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> List<ValidateBean> validate(T bean) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, Default.class);
        return errors2ValidateBeanList(constraintViolations);
    }

    /**
     * 校验属性
     * @param bean
     * @param property
     * @param <T>
     * @return
     */
    public static <T> List<ValidateBean> validateProperty(T bean, String property) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validateProperty(bean, property, Default.class);
        return errors2ValidateBeanList(constraintViolations);
    }

    /**
     * 校验属性值
     * @param bean
     * @param property
     * @param propertyValue
     * @param <T>
     * @return
     */
    public static <T> List<ValidateBean> validateValue(T bean, String property, Object propertyValue) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validateValue(bean.getClass(), property, propertyValue, Default.class);
        return errors2ValidateBeanList(constraintViolations);
    }

    private static <T> List<ValidateBean> errors2ValidateBeanList(Set<? extends ConstraintViolation<?>> errors) {
        List<ValidateBean> validateBeans = new ArrayList<>();
        if (errors != null && errors.size() > 0) {
            for (ConstraintViolation<?> cv : errors) {
                //这里循环获取错误信息，可以自定义格式
                String property = cv.getPropertyPath().toString();
                String message = cv.getMessage();
                validateBeans.add(new ValidateBean(property, message));
            }
        }
        return validateBeans;
    }
}
