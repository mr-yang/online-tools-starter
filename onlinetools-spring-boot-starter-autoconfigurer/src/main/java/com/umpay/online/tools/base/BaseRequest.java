package com.umpay.online.tools.base;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/9 11:44
 * @Description:请求的父类
 */
public class BaseRequest {

    /**
     * 每个请求的唯一标识
     */
    private String rpid;

    /**
     * 请求日期
     */
    private String reqDate;

    /**
     * 请求时间
     */
    private String reqTime;

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }
}
