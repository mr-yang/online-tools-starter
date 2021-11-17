package com.umpay.online.tools.config;

import com.umpay.online.tools.annotation.ConditionalOnPropertyByYml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description: 通过配合自定义注解@ConditionalOnPropertyByYml来控制是否加载此yml解析类
 * @Author: tianxiaoyang
 * @Date: 2021/8/4 2:32 下午
 **/
@Component
@ConditionalOnPropertyByYml
public class YamlPropertySourceFactory implements PropertySourceFactory {
    //普通日志打印
    private Logger logger = LoggerFactory.getLogger(YamlPropertySourceFactory.class);

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        Properties propertiesFromYaml = loadYamlToProperties(encodedResource);
        String sourceName = name != null ? name : encodedResource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    private Properties loadYamlToProperties(EncodedResource encodedResource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
            yamlPropertiesFactoryBean.setResources(encodedResource.getResource());
            yamlPropertiesFactoryBean.afterPropertiesSet();
            return yamlPropertiesFactoryBean.getObject();
        } catch (IllegalStateException e) {
            logger.error("YamlPropertySourceFactory 自定义yml解析类解析异常",e);
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
}
