package com.example.demo.controller;

import com.example.demo.service.TestHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.umpay.online.tools.log.LoggerPlus;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tianxiaoyang
 * @date 2021-11-29 10:35
 * @describe
 */
@RestController
public class TestHystrixController {

    @Resource
    private TestHystrixService testHystrixService;
    @RequestMapping("/testHystrix")
    public String testHystrix(@RequestBody String str){
        return testHystrixService.testHystrix(str);
    }


}
