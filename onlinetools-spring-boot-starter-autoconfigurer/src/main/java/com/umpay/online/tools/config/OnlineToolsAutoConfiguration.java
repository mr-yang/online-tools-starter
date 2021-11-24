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
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
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

    //安全传输层协议
    private static final String PROTOCOL = "TLS";
    // JKS/PKCS12
    private static final String KEY_KEYSTORE_TYPE = "PKCS12";
    @Resource
    private OnlineToolsConfig onlineToolsConfig;


    @Bean("okHttpClient")
    public OkHttpClient okHttpClient() {
        OkHttpConfig okhttp = onlineToolsConfig.getOkhttp();
        if (okhttp == null) {
            onlineToolsConfig.setOkhttp(new OkHttpConfig());
            LoggerTools.info("没有配置okhttp相关配置，自动创建一个默认的配置", okhttp);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(okhttp.isEnabledHttps()){
            setSslSocketFactory(builder,okhttp);
        }else{
            builder.sslSocketFactory(sslSocketFactory(),x509TrustManager());
        }
        return builder
                // 是否开启缓存
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(okhttp.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(okhttp.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(okhttp.getWriteTimeout(), TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> {
                    LoggerTools.debug("hostname:" + hostname);
                    return true;
                })
                // 拦截器
                .addNetworkInterceptor(getHttpLoggingInterceptor())
                .build();
    }

    /**
     * 设置https双向验证
     * 参考文章：https://blog.csdn.net/cs373616511/article/details/105628692
     * https://blog.csdn.net/lmj623565791/article/details/48129405
     * @param builder
     * @param okhttp
     */
    private void setSslSocketFactory(OkHttpClient.Builder builder, OkHttpConfig okhttp) {
        String cerPath = okhttp.getCerPath();
        String p12Path = okhttp.getP12Path();
        Assert.notNull(cerPath, "https 公钥路径为空");
        Assert.notNull(p12Path, "https 私钥路径为空");
        InputStream cerInputStream = null;
        InputStream p12InputStream = null;
        try {
            cerInputStream = new FileInputStream(ResourceUtils.getURL(cerPath).getPath());
            p12InputStream = new FileInputStream(ResourceUtils.getURL(p12Path).getPath());
            KeyManager[] keyManagers = getKeyManagers(p12InputStream, okhttp.getPassword());
//            TrustManager[] trustManagers = getTrustManagers(cerInputStream);
            TrustManager[] trustManagers = new TrustManager[]{x509TrustManager()};
            SSLContext sslContext = getSslContext(keyManagers, trustManagers);
            builder.sslSocketFactory(sslContext.getSocketFactory(),(X509TrustManager) trustManagers[0]);
        } catch (Exception e) {
            throw new RuntimeException("创建sslSocketFactory异常",e);
        } finally {
            if (cerInputStream != null) {
                try {
                    cerInputStream.close();
                } catch (IOException e) {
                    LoggerTools.error("创建sslSocketFactory异常",e);
                }
            }
            if (p12InputStream != null) {
                try {
                    p12InputStream.close();
                } catch (IOException e) {
                    LoggerTools.error("创建sslSocketFactory异常",e);
                }
            }
        }
    }

    @Bean("restTemplate")
    public RestTemplate okHttp3RestTemplate(@Qualifier("okHttpClient") OkHttpClient okHttpClient) {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
        return restTemplate;
    }

    private  SSLContext getSslContext(KeyManager[] keyManagers, TrustManager[] trustManagers) throws Exception {
        SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        return sslContext;
    }


    private  KeyManager[] getKeyManagers(InputStream inputStream, String password) throws Exception {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
        if(StringUtils.isEmpty(password)){
            //加载证书
            keyStore.load(inputStream, null);
            keyManagerFactory.init(keyStore, null);
        }else{
            //加载证书
            keyStore.load(inputStream, password.toCharArray());
            keyManagerFactory.init(keyStore, password.toCharArray());
        }
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        return keyManagers;
    }

    private  TrustManager[] getTrustManagers(InputStream inputStream) throws Exception {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = KeyStore.getInstance(KEY_KEYSTORE_TYPE);
        //加载证书
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate ca = certificateFactory.generateCertificate(inputStream);
        keyStore.load(null);
        //设置公钥
        keyStore.setCertificateEntry("bank_server", ca);
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }

    /**
     * 设置日志拦截器的等级
     *
     * @return
     */
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        OkHttpConfig okhttp = onlineToolsConfig.getOkhttp();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new OkHttp3LoggingInterceptor());
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        if (StringUtils.isNotBlank(okhttp.getLogLevel())) {
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
                return new X509Certificate[]{};
            }
        };
    }

    public SSLSocketFactory sslSocketFactory() {
        try {
            // 信任任何链接
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
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
}
