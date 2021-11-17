package com.umpay.online.tools.aspect;


import org.aspectj.lang.annotation.Pointcut;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-11 10:24
 * @Description:打印日志和字段校验统一切面切点
 */
public class AspectPointcut {
    /**
     * 格式：execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?
     * name-pattern(param-pattern) throws-pattern?)
     */
    @Pointcut("execution(* com.umpay.*.*.controller.*.*(..))")
    public void package1() {
    }

    @Pointcut("execution(* com.umpay.*.controller.*.*(..))")
    public void package2() {
    }

    @Pointcut("@annotation(com.umpay.online.tools.annotation.ExcludeMpspLog)")
    public void excludeMpspLog() {
    }

    @Pointcut("@annotation(com.umpay.online.tools.annotation.MpspLog)")
    public void mpspLog() {
    }
}
