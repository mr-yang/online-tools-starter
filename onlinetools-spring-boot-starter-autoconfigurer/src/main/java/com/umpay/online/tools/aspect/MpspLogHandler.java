package com.umpay.online.tools.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umpay.online.tools.config.MpspConfig;
import com.umpay.online.tools.exception.ExceptionCatch;
import com.umpay.online.tools.exception.OnlineException;
import com.umpay.online.tools.log.LoggerPlus;
import com.umpay.online.tools.util.LoggerTools;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: xiaoyang
 * @Date: 2020-07-10 15:37
 * @Description: 打印日志处理类，由于切点没有办法配置，依赖者可能需要自定义切点，
 * 但是需要自己处理打印日志，这样提出来就可以提供依赖者使用了。
 */
@Component
public class MpspLogHandler {

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        long beginTime = 0;
        Object[] args;
        JSONObject jsonObject = new JSONObject();
        try {
            printMethodName(pjp);
            args = pjp.getArgs();
            addRequest(jsonObject, args);
            //开始时间
            beginTime = System.currentTimeMillis();
            result = pjp.proceed();
            return result;
        } catch (OnlineException onlineException) {
            //捕获自定义异常就是为了把响应的数据放到简要日志中
            JSONObject jsonObjectData = onlineException.getJSONObjectData();
            jsonObject.putAll(jsonObjectData);
            printResLog(jsonObjectData);
            throw onlineException;
        } catch (Exception exception) {
            //捕获全局异常就是为了把retCode和redMsg放到简要日志中
            Map<String, String> codeAndRetMsg = ExceptionCatch.getDefaultRetCodeAndRetMsg();
            jsonObject.putAll(codeAndRetMsg);
            printResLog(codeAndRetMsg);
            throw exception;
        } finally {
            try {
                //结束时间
                long endTime = System.currentTimeMillis();
                long time_consuming = endTime - beginTime;
                addResponse(jsonObject, result);
                printMpsp(jsonObject, time_consuming);
            } catch (Exception e) {
                LoggerTools.error("打印mpsp日志异常，addResponse(),printMpsp()", e);
            }
        }
    }

    /**
     * 打印controller的方法方便查新
     * @param pjp
     */
    private void printMethodName(ProceedingJoinPoint pjp) {
        try {
            Signature signature = pjp.getSignature();
            String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
            LoggerTools.info("controller的方法", methodName);
        } catch (Exception e) {
            LoggerTools.error("打印ontroller的方法异常", e);
        }

    }


    private void printResLog(Object object) {
        LoggerTools.info("服务端响应参数", object);
    }

    /**
     * 打印简要日志
     *
     * @param jsonObject
     * @param time_consuming
     */
    private void printMpsp(JSONObject jsonObject, Long time_consuming) {
        jsonObject.put(MpspConfig.CONSUMER_TIME, String.valueOf(time_consuming));
        LoggerPlus.mpsp(jsonObject);
    }

    /**
     * 把响应放到待打印json对象中
     *
     * @param jsonObject
     * @param result
     */
    private void addResponse(JSONObject jsonObject, Object result) {
        if (result != null && filterParameter(result)) {
            printResLog(result);
            jsonObject.putAll(JSONObject.parseObject(JSON.toJSONString(result)));
        }
    }

    /**
     * 把请求对象中的参数都放到待打印json对象中
     *
     * @param jsonObject
     * @param args
     */
    private void addRequest(JSONObject jsonObject, Object[] args) {
        try {
            if (args == null || args.length < 1) {
                LoggerTools.info("该方法没有参数，不需要打印客户端请求参数");
                return;
            }
            for (Object arg : args) {
                if (filterParameter(arg)) {
                    JSONObject jsonObjectArg = JSONObject.parseObject(JSON.toJSONString(arg));
                    jsonObject.putAll(jsonObjectArg);
                }
            }
            LoggerTools.info("客户端请求参数", jsonObject);
        } catch (Exception e) {
            LoggerTools.error("打印mpsp日志异常，addRequest()", e);
        }
    }

    /**
     * 判断是否需要打印简要日志
     *
     * @param arg
     * @return
     */
    protected boolean filterParameter(Object arg) {
        return !(arg == null || arg instanceof String || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse);
    }
}
