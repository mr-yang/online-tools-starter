package com.umpay.online.tools.config;

import com.umpay.online.tools.util.LoggerTools;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/15 17:03
 * @Description:打印简要日志配置类
 */
public class MpspConfig {

    //逗号分隔
    public static final String COMMA = ",";
    //打印日志默认显示内容
    public static final String LOG_HEADER = "default";
    //mpsp日志打印耗时时间
    public static final String CONSUMER_TIME = "consumeTime";
    public static String[] mpspLogField;
    /**
     * 简要日志字段
     */
    private String field;


    /**
     * 是否开启打印简要日志
     */
    private boolean enabledMpsp;
    /**
     * 是否开启生成rpid并修改线程id
     */
    private boolean enabledGenerateRpid;
    /**
     * 是否开启接口字段校验
     */
    private boolean enabledValidated;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public void postConstruct() {
        //1.逗号分隔mpsp简要日志字段
        if (StringUtils.isBlank(field)) {
            LoggerTools.error("没有配置mpsp的field配置，这可能影响简要日志的打印");
        }else{
            mpspLogField = field.split(COMMA);
        }
    }

    public boolean isEnabledMpsp() {
        return enabledMpsp;
    }

    public void setEnabledMpsp(boolean enabledMpsp) {
        this.enabledMpsp = enabledMpsp;
    }

    public boolean isEnabledGenerateRpid() {
        return enabledGenerateRpid;
    }

    public void setEnabledGenerateRpid(boolean enabledGenerateRpid) {
        this.enabledGenerateRpid = enabledGenerateRpid;
    }

    public boolean isEnabledValidated() {
        return enabledValidated;
    }

    public void setEnabledValidated(boolean enabledValidated) {
        this.enabledValidated = enabledValidated;
    }
}
