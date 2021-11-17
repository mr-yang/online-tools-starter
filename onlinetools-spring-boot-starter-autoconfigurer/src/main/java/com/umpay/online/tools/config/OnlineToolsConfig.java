package com.umpay.online.tools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author tianxiaoyang
 * @Date: 2020-06-12 15:59
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "online.tools")
public class OnlineToolsConfig {

    private MpspConfig mpsp;

    private OkHttpConfig okhttp;

    private ExceptionConfig exception;


    public MpspConfig getMpsp() {
        return mpsp;
    }

    public void setMpsp(MpspConfig mpsp) {
        this.mpsp = mpsp;
    }

    public OkHttpConfig getOkhttp() {
        return okhttp;
    }

    public void setOkhttp(OkHttpConfig okhttp) {
        this.okhttp = okhttp;
    }

    public ExceptionConfig getException() {
        return exception;
    }

    public void setException(ExceptionConfig exception) {
        this.exception = exception;
    }

    @PostConstruct
    public void postConstruct() {
        if (mpsp == null) {
            throw new RuntimeException("没有配置mpsp相关配置，请在yml中配置");
        } else {
            mpsp.postConstruct();
        }
    }
}
