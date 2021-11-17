package com.umpay.online.tools.enums;

/**
* @Description: 返回码和返回语的枚举类，不要在这个类添加任务返回码和返回语，
 * 如果添加，需要每个平台自己创建，可以参考碰一碰核心的
 * com.online.unionpaybusi.enums.CoreRetMsgEnum
* @Author: xiaoyang
* @Date: 2020-03-17 13:42
*/
public enum RetMsgEnum implements RetMsgEnumInterface{

    SUCCESS("0000", "处理成功"),
    NOT_FOUND("20000404", "请求异常"),
    CONNECT_TIMED_OUT("400001", "连接超时"),
    TIME_OUT("9001", "读超时"),
    VERIFICATION_FAIL("66666666", "字段校验未通过"),
    FAIL("9999","未知错误");


    private String code;
    private String msg;
    RetMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Override
    public String getRetCode() {
        return code;
    }
    @Override
    public String getRetMsg() {
        return msg;
    }
}