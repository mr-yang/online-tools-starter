package com.umpay.online.tools.validator;

/**
 * @author tianxiaoyang
 */
public enum DateEnum {

    /**
     * YYYYMMDD
     */
    YYYYMMDD("yyyyMMdd","^[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$"),
    /**
     * YYYY_MM_DD
     */
    YYYY_MM_DD("yyyy-MM-dd","^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");

    /**
     * 正则表达式
     */
    private final String regx;

    /**
     * 正则表达式
     */
    private final String pattern;


    public String getPattern() {
        return pattern;
    }

    public String getRegx() {
        return regx;
    }

    DateEnum(String pattern,String regx){
        this.pattern = pattern;
        this.regx = regx;
    }


}
