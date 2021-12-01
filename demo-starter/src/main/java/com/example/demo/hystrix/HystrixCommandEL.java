package com.example.demo.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import java.lang.annotation.Annotation;
import java.util.Objects;
/**
 * @author tianxiaoyang
 * @date 2021-11-30 10:40
 * @describe Hystrix commandKey,groupKey,threadPoolKey的el表达式解析器
 */
public class HystrixCommandEL implements HystrixCommand {

    private String[] names;
    private Object[] args;

    ExpressionParser parser = new SpelExpressionParser();
    StandardEvaluationContext context = new StandardEvaluationContext();

    private HystrixCommand hystrixCommand;

    public HystrixCommandEL(HystrixCommand hystrixCommand, String[] names, Object[] args) {
        this.hystrixCommand = hystrixCommand;
        this.args = args;
        this.names = names;
    }

    @Override
    public String groupKey() {
        return parseEL(hystrixCommand.groupKey());
    }

    @Override
    public String commandKey() {
        return parseEL(hystrixCommand.commandKey());
    }

    @Override
    public String threadPoolKey() {
        return parseEL(hystrixCommand.threadPoolKey());
    }

    @Override
    public String fallbackMethod() {
        return parseEL(hystrixCommand.fallbackMethod());
    }

    @Override
    public HystrixProperty[] commandProperties() {
        return hystrixCommand.commandProperties();
    }

    @Override
    public HystrixProperty[] threadPoolProperties() {
        return hystrixCommand.threadPoolProperties();
    }

    @Override
    public Class<? extends Throwable>[] ignoreExceptions() {
        return hystrixCommand.ignoreExceptions();
    }

    @Override
    public ObservableExecutionMode observableExecutionMode() {
        return hystrixCommand.observableExecutionMode();
    }

    @Override
    public HystrixException[] raiseHystrixExceptions() {
        return hystrixCommand.raiseHystrixExceptions();
    }

    @Override
    public String defaultFallback() {
        return parseEL(hystrixCommand.defaultFallback());
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return hystrixCommand.annotationType();
    }

    private String parseEL(String key) {
        // 为了效率和安全考虑目前只允许包含#的属性调用，如果想支持全部SpEl,将这个if全部去掉就行(比如，如果无参数，只是做计算，判断names,args就不合适了)
        if (!StringUtils.contains(key, "#") || isEmpty(args) || isEmpty(names)) {
            return key;
        }
        for (int i = 0; i < names.length; i++) {
            context.setVariable(names[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

    private boolean isEmpty(Object [] objs){
        return Objects.isNull(objs) || objs.length==0;
    }
}


