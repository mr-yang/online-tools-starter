package com.umpay.online.tools.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-11 10:02
 * @Description:字段校验切面，优先级低于打印日志的切面，前置切面只用于校验字段
 */
@Component
@Aspect
@Order(3)
@ConditionalOnProperty(prefix = "online.tools.mpsp", name = "enabledValidated",matchIfMissing = true,havingValue = "true")
public class ValidatedAspect extends AspectPointcut {

    @Autowired
    private ValidatedHandler validatedHandler;

    @Before("(mpspLog()||package1()|| package2()) && !excludeMpspLog()")
    public void doBefore(JoinPoint point) throws Throwable {
        validatedHandler.doBefore(point);
    }
}
