package com.umpay.online.tools.aspect;

import com.umpay.online.tools.annotation.IgnoreValidate;
import com.umpay.online.tools.base.BaseResponse;
import com.umpay.online.tools.enums.RetMsgEnum;
import com.umpay.online.tools.exception.OnlineException;
import com.umpay.online.tools.util.LoggerTools;
import com.umpay.online.tools.util.ValidatorUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author tianxiaoyang
 * @Date: 2020-07-10 15:43
 * @Description:
 */
@Component
public class ValidatedHandler {
    @Autowired
    private MpspLogHandler mpspLogHandler;

    public void doBefore(JoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            //校验参数，JDK1.8写法
            Optional<List<ValidateBean>> optional = Arrays.stream(args)
                    .filter(arg -> isValidate(arg))
                    .map(arg -> ValidatorUtil.validate(arg))
                    .filter(validateBeans -> validateBeans.size() > 0).findFirst();
            if (optional.isPresent()) {
                LoggerTools.debug("接口字段校验失败，请求数据为", args);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRetCode(RetMsgEnum.VERIFICATION_FAIL.getRetCode());
                baseResponse.setRetMsg(optional.get().toString());
                baseResponse.setResDateAndResTime();
                //前面的拦截器已经把rpid修改了线程名称，这里直接使用
                baseResponse.setRpid(Thread.currentThread().getName());
                //说明字段校验没有通过，通过自定义异常抛出
                throw new OnlineException(baseResponse);
            }
        }
    }

    //默认校验所有接口字段，如果不要校验需要在类上面添加IgnoreValidate注解
    private boolean isValidate(Object arg) {
        //默认校验接口字段
        IgnoreValidate ignoreValidate = arg.getClass().getAnnotation(IgnoreValidate.class);
        if (ignoreValidate == null) {
            return mpspLogHandler.filterParameter(arg);
        }
        return false;
    }


    //默认不校验接口字段的逻辑，如果需要校验需要再类上面添加Validated或Valid注解
//    private boolean isValidate(Object arg){
//        Validated validated = arg.getClass().getAnnotation(Validated.class);
//        Valid valid = arg.getClass().getAnnotation(Valid.class);
//        if(validated != null || valid != null){
//            return filterParameter(arg);
//        }
//        return false;
//    }
}
