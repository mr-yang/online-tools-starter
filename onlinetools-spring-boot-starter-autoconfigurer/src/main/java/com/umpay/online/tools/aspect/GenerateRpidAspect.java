package com.umpay.online.tools.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-14 11:25
 * @Description:生成ripid并修改线程id
 */
@Component
@Aspect
@Order(1)
@ConditionalOnProperty(prefix = "online.tools.mpsp", name = "enabledGenerateRpid",matchIfMissing = true,havingValue = "true")
public class GenerateRpidAspect extends AspectPointcut {
    @Autowired
    private GenerateRpidHandler generateRpidHandler;

    @Around("(mpspLog()||package1()|| package2()) && !excludeMpspLog()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        return generateRpidHandler.doAround(point);
    }
}
