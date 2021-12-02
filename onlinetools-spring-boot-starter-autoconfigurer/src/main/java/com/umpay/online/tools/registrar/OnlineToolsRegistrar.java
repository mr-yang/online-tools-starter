package com.umpay.online.tools.registrar;

import com.umpay.online.tools.annotation.EnableOnlineTools;
import com.umpay.online.tools.log.LoggerPlus;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author tianxiaoyang
 * @date 2021-07-09 10:17
 * @describe 配置Registrar，自动扫描启动器下面的配置或组件
 */
public class OnlineToolsRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, true);
        //改造成自动获取上级包名
        scanner.scan("com.umpay.online.tools");
    }
}
