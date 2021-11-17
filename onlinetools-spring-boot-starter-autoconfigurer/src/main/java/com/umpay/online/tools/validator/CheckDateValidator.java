package com.umpay.online.tools.validator;


import com.umpay.online.tools.util.LoggerTools;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author tianxiaoyang
 * @Date: 2019-08-28 09:49
 * @Description:检查日期格式是否正确，并检查上传的日期是否大于等于或小于等于当前日期
 */
public class CheckDateValidator implements ConstraintValidator<CheckDate, String> {
    private String message;
    private SimpleDateFormat simpleDateFormat;
    private boolean empty;
    private OperatorEnum operator;
    private Pattern pattern;

    @Override
    public void initialize(CheckDate constraintAnnotation) {
        message = constraintAnnotation.message();
        simpleDateFormat = new SimpleDateFormat(constraintAnnotation.pattern().getPattern());
        empty = constraintAnnotation.empty();
        operator = constraintAnnotation.operator();
        pattern = Pattern.compile(constraintAnnotation.pattern().getRegx());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //1.如果日期为null
        if (StringUtils.isEmpty(value)) {
            //如果字段为null并允许为null直接返回true
            if(empty){
                return true;
            }
        }
        //2.校验正则
        if (!pattern.matcher(value).matches()) {
            setMessage(context);
            LoggerTools.debug("正则校验失败");
            return false;
        }
        //不需要判断日期
        if (OperatorEnum.NONE.equals(operator)){
            return true;
        }
        //3.校验格式是否正确
        Date date ;
        try {
            date = simpleDateFormat.parse(value);
        } catch (ParseException e) {
            LoggerTools.error("转换上送的日期出现异常",e);
            setMessage(context);
            return false;
        }

        Date tempDate = new Date();
        String currentDateStr = simpleDateFormat.format(tempDate);

        //4.获取比较运算符
        if (OperatorEnum.E.equals(operator)) {
            if(currentDateStr.equals(value)){
                return true;
            }else{
                setMessage(context);
                LoggerTools.debug("日期不相等");
                return false;
            }
        }
        Date currentDate ;
        try {
            currentDate = simpleDateFormat.parse(currentDateStr);
        } catch (ParseException e) {
            LoggerTools.error("转换当前时间出现异常",e);
            setMessage(context);
            return false;
        }
        if (OperatorEnum.LTE.equals(operator)) {
            if(date.getTime() <= currentDate.getTime()){
                return true;
            }else{
                LoggerTools.debug("日期不小于等于当前日期");
                setMessage(context);
                return false;
            }
        }
        if (OperatorEnum.GTE.equals(operator)) {
            if(date.getTime() >= currentDate.getTime()){
                return true;
            }else{
                LoggerTools.debug("日期不大于等于当前日期");
                setMessage(context);
                return false;
            }
        }
        LoggerTools.debug("未知错误");
        setMessage(context);
        return false;
    }

    private void setMessage(ConstraintValidatorContext context){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

    }
}
