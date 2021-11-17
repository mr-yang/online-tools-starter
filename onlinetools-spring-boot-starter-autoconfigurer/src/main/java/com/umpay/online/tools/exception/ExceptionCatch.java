package com.umpay.online.tools.exception;

import com.umpay.online.tools.base.BaseResponse;
import com.umpay.online.tools.config.OnlineToolsConfig;
import com.umpay.online.tools.enums.RetMsgEnum;
import com.umpay.online.tools.enums.RetMsgEnumInterface;
import com.umpay.online.tools.util.LoggerTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/9 10:08
 * @Description:全局异常捕获类，通用代码，如果自己平台需要有特殊逻辑，可以继承这个类， 参考com.online.unionpaybusi.exception.CoreOnlineException
 */
@RestControllerAdvice
public class ExceptionCatch {

    @Autowired
    private OnlineToolsConfig onlineToolsConfig;

    protected static HashMap<Class<?>, RetMsgEnumInterface> EXCEPTIONS = new HashMap<>();
    private static HashMap<String, String> map = new HashMap<>();
    protected static HashMap<String, RetMsgEnumInterface> CONTROLLER_EXCEPTIONS = new HashMap<>();

    static {

        //加入默认的返回码和返回语
        map.put("retCode", RetMsgEnum.FAIL.getRetCode());
        map.put("retMsg", RetMsgEnum.FAIL.getRetMsg());

        //在这里加入一些基础的异常类型判断 builder.put(HttpMessageNotReadableException.class,RetMsgEnum.FAIL);
        //这里配置Controller方法对应的返回语
        //CONTROLLER_EXCEPTIONS.put("com.online.unionpaybusi.controller.UserController.register", RetMsgEnum.NOT_FOUND);
    }

    /**
     * 捕获自定义OnlineException异常，子类也能捕获到
     *
     * @param exception 自定义OnlineException异常
     * @return 通用返回参数
     */
    @ExceptionHandler(OnlineException.class)
    public BaseResponse customException(OnlineException exception) {
        BaseResponse data = exception.getData();
        return data;
    }


    /**
     * 自己没有捕获到的异常会在这个方法中捕获
     *
     * @param exception 自己没有捕获到的异常
     * @return 通用返回参数
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse exception(Exception exception) {
        RetMsgEnumInterface RetMsgEnumInterface = EXCEPTIONS.get(exception.getClass());
        if (RetMsgEnumInterface != null) {
            return BaseResponse.create(RetMsgEnumInterface.getRetCode(),RetMsgEnumInterface.getRetMsg());
        }
        if (onlineToolsConfig.getException() != null && onlineToolsConfig.getException().isEnabled() && StringUtils.isNotEmpty(onlineToolsConfig.getException().getPackageName())) {
            RetMsgEnumInterface = getRetMsgInfo(exception);
            if (RetMsgEnumInterface != null) {
                return BaseResponse.create(RetMsgEnumInterface.getRetCode(),RetMsgEnumInterface.getRetMsg());
            }
        }
        //只打印没有配置的异常日志
        LoggerTools.error("全局异常类捕获未知异常", exception);
        return BaseResponse.fail();
    }

    private RetMsgEnumInterface getRetMsgInfo(Exception exception) {
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            String methodName = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
            if (methodName.startsWith(onlineToolsConfig.getException().getPackageName())) {
                RetMsgEnumInterface RetMsgEnumInterface = CONTROLLER_EXCEPTIONS.get(methodName);
                if (RetMsgEnumInterface != null) {
                    return RetMsgEnumInterface;
                }
            }
        }
        return null;
    }


    /**
     * 非自定义异常返回特殊的返回码，提供给mpsp切面使用
     *
     * @return
     */
    public static Map<String, String> getDefaultRetCodeAndRetMsg() {
        return map;
    }
}
