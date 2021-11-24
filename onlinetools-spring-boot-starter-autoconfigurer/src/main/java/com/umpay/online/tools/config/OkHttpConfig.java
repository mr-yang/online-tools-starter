package com.umpay.online.tools.config;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/9 14:02
 * @Description:配置OKHttp的类
 */
public class OkHttpConfig {

    /**
     * 连接超时时间，单位秒
     */
    private Integer connectTimeout = 5;
    /**
     * 读超时时间，单位秒
     */
    private Integer readTimeout =35;
    /**
     * 写超时时间，单位秒
     */
    private Integer writeTimeout = 5;
    /**
     * 连接池中整体的空闲连接的最大数量
     */
    private Integer maxIdleConnections = 200;
    /**
     * 连接空闲时间最多为 300 秒
     */
    private Long keepAliveDuration = 300L;
    /**
     * 配置okhttp打印的日志等级，可配置，从小到大依次为
     * NONE，无记录（DEFAULT）。
     * BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
     * HEADERS，记录基本信息以及请求和响应头。
     * BODY，记录请求和响应的头文件，正文和元数据
     */
    private String logLevel = "BODY";


    /**
     * 是否开启https双向验证
     */
    private boolean enabledHttps = false;

    /**
     * 公钥路径，cer格式
     */
    private String cerPath;
    /**
     * 私钥路径，p12格式
     */
    private String p12Path;

    /**
     * 私钥密码，没有可以不设置
     */
    private String password;


    public boolean isEnabledHttps() {
        return enabledHttps;
    }

    public void setEnabledHttps(boolean enabledHttps) {
        this.enabledHttps = enabledHttps;
    }

    public String getCerPath() {
        return cerPath;
    }

    public void setCerPath(String cerPath) {
        this.cerPath = cerPath;
    }

    public String getP12Path() {
        return p12Path;
    }

    public void setP12Path(String p12Path) {
        this.p12Path = p12Path;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public Long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }


    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
