package com.umpay.online.tools.annotation;

import com.umpay.online.tools.registrar.OnlineToolsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author tianxiaoyang
 * @date 2021-11-11 15:36
 * @describe 是否开启自动配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//配置Registrar，自动扫描启动器下面的配置或组件
@Import(OnlineToolsRegistrar.class)
public @interface EnableOnlineTools {
}
