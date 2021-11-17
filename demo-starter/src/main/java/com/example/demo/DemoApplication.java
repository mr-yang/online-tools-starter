package com.example.demo;

import com.umpay.online.tools.annotation.EnableOnlineTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tianxiaoyang
 */
@SpringBootApplication
//开始
@EnableOnlineTools
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
