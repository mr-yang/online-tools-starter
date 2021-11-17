package com.umpay.online.tools.config;

import com.umpay.online.tools.http.OkHttp3LoggingInterceptor;
import com.umpay.online.tools.registrar.OnlineToolsRegistrar;
import com.umpay.online.tools.util.LoggerTools;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author tianxiaoyang
 * @Date: 2020-06-12 14:39
 * @Description:
 */
@Configuration//申明这是一个配置类
@ConditionalOnWebApplication//引用启动器的项目是web应用此自动配置模块才生效
@EnableConfigurationProperties({OnlineToolsConfig.class})//加载配置对象到容器
public class OnlineToolsAutoConfiguration {


    @Autowired
    private OnlineToolsConfig onlineToolsConfig;

    @Bean("okHttpClient")
    public OkHttpClient okHttpClient() {
        if (onlineToolsConfig.getOkhttp() == null) {
            onlineToolsConfig.setOkhttp(new OkHttpConfig());
            LoggerTools.info("没有配置okhttp相关配置，自动创建一个默认的配置",onlineToolsConfig.getOkhttp());
        }
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                // 是否开启缓存
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(onlineToolsConfig.getOkhttp().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(onlineToolsConfig.getOkhttp().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(onlineToolsConfig.getOkhttp().getWriteTimeout(), TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                // 拦截器
                .addNetworkInterceptor(getHttpLoggingInterceptor())
                .build();
    }


    /**
     * 设置日志拦截器的等级
     * @return
     */
    public HttpLoggingInterceptor getHttpLoggingInterceptor(){
        OkHttpConfig okhttp = onlineToolsConfig.getOkhttp();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new OkHttp3LoggingInterceptor());
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        if(StringUtils.isNotBlank(okhttp.getLogLevel())){
            String logLevel = okhttp.getLogLevel().toUpperCase();
            switch (logLevel) {
                case "NONE":
                    level = HttpLoggingInterceptor.Level.NONE;
                    break;
                case "BASIC":
                    level = HttpLoggingInterceptor.Level.BASIC;
                    break;
                case "HEADERS":
                    level = HttpLoggingInterceptor.Level.HEADERS;
                    break;
            }
        }
        logInterceptor.setLevel(level);
        return logInterceptor;
    }

    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public SSLSocketFactory sslSocketFactory() {
        try {
            // 信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            LoggerTools.error("创建SSLSocketFactory异常", e);
        }
        return null;
    }

    public ConnectionPool pool() {
        return new ConnectionPool(onlineToolsConfig.getOkhttp().getMaxIdleConnections(), onlineToolsConfig.getOkhttp().getKeepAliveDuration(), TimeUnit.SECONDS);
    }

    @Bean("restTemplate")
    public RestTemplate okHttp3RestTemplate(@Qualifier("okHttpClient") OkHttpClient okHttpClient) {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
        return restTemplate;
    }
}
