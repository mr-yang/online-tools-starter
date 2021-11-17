package com.umpay.online.tools.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umpay.online.tools.exception.OnlineException;
import com.umpay.online.tools.util.LoggerTools;
import com.umpay.online.tools.util.SequenceGenerator;
import com.umpay.online.tools.util.ThreadChangeName;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tianxiaoyang
 * @Date: 2020-07-10 15:41
 * @Description: 修改线程id生成rpid处理类，由于切点没有办法配置，依赖者可能需要自定义切点，
 *  * 但是需要自己处理，这样提出来就可以提供依赖者使用了。
 */
@Component
public class GenerateRpidHandler {
    @Autowired
    private MpspLogHandler mpspLogHandler;
    public static final String RPID = "rpid";

    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        try {
            String rpid = null;
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (mpspLogHandler.filterParameter(args[i])) {
                        JSONObject jsonObjectArg = JSONObject.parseObject(JSON.toJSONString(args[i]));
                        if (jsonObjectArg.containsKey(RPID)) {
                            rpid = jsonObjectArg.getString(RPID);
                        }else{
                            rpid = SequenceGenerator.getSequence();
                            jsonObjectArg.put(RPID,rpid);
                        }
                        args[i] = JSON.parseObject(jsonObjectArg.toJSONString(), args[i].getClass());
                        break;
                    }
                }
            }
            if(StringUtils.isEmpty(rpid)){
                rpid = SequenceGenerator.getSequence();
            }
            ThreadChangeName.changeThreadName(rpid);
        }catch (OnlineException throwable) {
            //业务流程异常不需要处理
        } catch (Throwable throwable) {
            LoggerTools.error("生成rpid切面异常",throwable);
        }
        return point.proceed(args);
    }
}
