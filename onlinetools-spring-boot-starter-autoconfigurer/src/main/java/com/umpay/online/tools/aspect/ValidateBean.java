package com.umpay.online.tools.aspect;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-11 10:51
 * @Description:
 */
public class ValidateBean {

    private String property;
    private String message;

    public ValidateBean() {}

    public ValidateBean(String property, String message) {
        this.property = property;
        this.message = message;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(property);
        sb.append(message);
        return sb.toString();
    }
}
