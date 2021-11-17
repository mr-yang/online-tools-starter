package com.umpay.online.tools.validator;

/**
 * @author tianxiaoyang
 */
public enum EncodingEnum {

    /**
     * UTF-8
     */
    UTF("UTF-8","***"),
    /**
     * GB2312
     */
    GBK("GB2312","**");

    public String getEncoding() {
        return encoding;
    }

    public String getPlaceholder(){
        return placeholder;
    }
    /**
     *编码占位符
     */
    private final String placeholder;
    /**
     *编码方式
     */
    private final String encoding;

    EncodingEnum(String encoding,String placeholder){
        this.encoding = encoding;
        this.placeholder = placeholder;
    }


}
