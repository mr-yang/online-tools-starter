package com.umpay.online.tools.config;

/**
 * @author tianxiaoyang
 * @Date: 2020-06-12 16:02
 * @Description:
 */
public class ExceptionConfig {

    private boolean enabled;
    private String packageName;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
