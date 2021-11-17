package com.umpay.online.tools.config;

import com.umpay.online.tools.annotation.ConditionalOnPropertyByYml;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description: 动态获取配置文件，只支持yml格式的配置文件。
 * Spring高级之注解@PropertySource详解（超详细）
 * 参考文章：https://blog.csdn.net/qq_40837310/article/details/106587158
 * @ConditionalOnPropertyByYml 自定义注解
 * 使用方式，默认不开启配置分离
 * 需要开启，只需要在响应的yml配置文件中配置 online.tools.property.name 文件路径即可
 * 例如：
 * 相对路径：online.tools.property.name="../config_dev/application.yml"
 * 绝对路径：online.tools.property.name="/Users/tianxiaoyang/project/config_dev/application.yml"
 * @Author: tianxiaoyang
 * @Date: 2021/8/4 1:55 下午
 **/

@PropertySource(factory = YamlPropertySourceFactory.class,
        value = {"file:${online.tools.property.name"})
@Configuration
@ConditionalOnPropertyByYml
public class PropertyConfig {
}
