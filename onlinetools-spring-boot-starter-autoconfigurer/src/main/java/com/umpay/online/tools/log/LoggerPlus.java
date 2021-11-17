package com.umpay.online.tools.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umpay.online.tools.config.MpspConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
* @Description: 日志打印工具类，增强版 
* @Author: xiaoyang 
* @Date: 2020/1/15 5:45 下午
 * https://blog.csdn.net/vcstrong/article/details/80527455
*/
@Deprecated
public class LoggerPlus {

    /**
     * 普通日志打印
     */
    private final static Logger logger = LoggerFactory.getLogger(LoggerPlus.class);
    /**
     * mpsp日志打印
     */
    private final static Logger mpspLogger = LoggerFactory.getLogger("mpspLogger");

    /**
     * @param obj void
     * @Description：mpsp日志打印 <p>创建人：xiaoyang ,  2020/1/15  上午9:41:25</p>
     * <p>修改人：xiaoyang ,  2020/1/15  上午9:41:25</p>
     */
    public static void mpsp(Object obj) {
        try {
            String printStr = getPrintValue(MpspConfig.COMMA, MpspConfig.mpspLogField, obj);
            mpspLogger.info(printStr);
        } catch (Exception e) {
            logger.error("打印mpsp日志异常", e);
        }
    }

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


	/**
	 * 拼接打印信息
	 *
	 * @param comma  连接服务
	 * @param fields 打印字段
	 * @param obj    传入对象
	 * @return
	 */
	public static String getPrintValue(String comma, String[] fields, Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		if (null != obj) {
			//对象转jsonObject
			JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
			if(fields !=null){
				//循环获取字段
				for (int i = 0; null != fields && i < fields.length; i++) {
					Object val = jsonObject.get(fields[i]);
					stringBuilder.append(val == null ? "" : val);
					stringBuilder.append(comma);
				}
			}
			//获取耗时时间
			Object consumerTimer = jsonObject.get(MpspConfig.CONSUMER_TIME);
			if (null != consumerTimer) {
				stringBuilder.append(consumerTimer);
			}
		}
		return stringBuilder.toString();
	}



}
