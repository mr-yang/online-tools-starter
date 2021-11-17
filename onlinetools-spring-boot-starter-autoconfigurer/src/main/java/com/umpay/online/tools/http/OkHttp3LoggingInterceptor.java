package com.umpay.online.tools.http;

import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tianxiaoyang
 * @Date: 2020-07-09 14:50
 * @Description: okhttp打印日志的拦截器
 */
public class OkHttp3LoggingInterceptor implements HttpLoggingInterceptor.Logger {

    /**
     * 普通日志打印
     */
    private Logger logger = LoggerFactory.getLogger(OkHttp3LoggingInterceptor.class);
    @Override
    public void log(String s) {
        logger.debug(s);
    }
}
