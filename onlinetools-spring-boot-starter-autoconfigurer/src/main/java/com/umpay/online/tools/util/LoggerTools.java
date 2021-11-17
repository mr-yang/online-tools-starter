package com.umpay.online.tools.util;

import com.alibaba.fastjson.JSON;
import com.umpay.online.tools.config.MpspConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
* @Description: 此logger只适合打印此启动器里面的日志，方便依赖者做日志过滤使用
* @Author: xiaoyang 
* @Date: 2020/1/15 5:45
*/
@Deprecated
public class LoggerTools {

    //普通日志打印
    private static Logger logger = LoggerFactory.getLogger("com.online.base.tools");



    /**
     * @param header 头信息，用于描述使用场景
     * @param obj    打印报文体
     *               void
     * @Description：info级别详细日志打印 <p>创建人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     */
    public static void info(String header, Object obj) {
        try {
			logger.info(String.format("header=[%s],context=%s", header, JSON.toJSONString(obj)));
		} catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }



    /**
     * @param header 头信息，用于描述使用场景
     * @param obj    打印报文体
     *               void
     * @Description：error级别详细日志打印 <p>创建人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     */
    public static void error(String header, Object obj) {
        try {
            logger.error(String.format("header=[%s]", header), obj);
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }

    /**
     * @param context 打印报文体
     *                void
     * @Description：error级别详细日志打印 <p>创建人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     */
    public static void error(String context) {
        try {
            logger.error(String.format("context=%s", context));
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }

    /**
     * @param obj void
     * @Description：打印info级别的日志，不带头 <p>创建人：xiaoyang ,  2020/1/15  上午9:43:22</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:43:22</p>
     */
    public static void info(Object obj) {
        info(MpspConfig.LOG_HEADER, JSON.toJSONString(obj));
    }

    /**
     * @param context void
     * @Description：打印字符串 <p>创建人：xiaoyang ,  2020/1/15  上午9:50:48</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:50:48</p>
     */
    public static void info(String context) {
        try {
            logger.info(String.format("context=%s", context));
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }


    /**
     * @param obj void
     * @Description：打印debug级别的详细日志 <p>创建人：xiaoyang ,  2020/1/15  上午9:44:03</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:44:03</p>
     */
    public static void debug(Object obj) {
        try {
            logger.debug(String.format("context=%s", JSON.toJSONString(obj)));
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }



    /**
     * @param header 头信息，用于描述使用场景
     * @param obj    打印报文体
     *               void
     * @Description：debug级别详细日志打印 <p>创建人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:42:23</p>
     */
    public static void debug(String header, Object obj) {
        try {
            logger.debug(String.format("header=[%s],context=%s", header, JSON.toJSONString(obj)));
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }



    /**
     * @param context void
     * @Description：打印debug级别日志 <p>创建人：xiaoyang ,  2020/1/15  上午9:51:44</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:51:44</p>
     */
    public static void debug(String context) {
        try {
            logger.debug(String.format("context=%s", context));
        } catch (Exception e) {
            logger.error("打印print日志异常", e);
        }
    }

}
