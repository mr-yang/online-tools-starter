package com.umpay.online.tools.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author tianxiaoyang
 * @Date: 2020/1/2 14:10
 * @Description: 修改线程id并打印mpsp日志，默认拦截4级或5级或带有MpspLog注解的类，
 * 排除ExcludeMpspLog注解的方法，如果包名需要自己特殊化需求可以关闭此切面（online.tools.mpsp.enabledMpsp=false），
 * 自己配置切面并调用mpspLogHandler.doAround(pjp)方法也可以
 * 参考博客：https://cloud.tencent.com/developer/article/1455539
 */
@Component
@Aspect
@Order(2)
@ConditionalOnProperty(prefix = "online.tools.mpsp", name = "enabledMpsp",matchIfMissing = true,havingValue = "true")
public class MpspLogAspect extends AspectPointcut {

    @Autowired
    private MpspLogHandler mpspLogHandler;

    @Around("(mpspLog()||package1()|| package2()) && !excludeMpspLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return mpspLogHandler.doAround(pjp);
    }

}
