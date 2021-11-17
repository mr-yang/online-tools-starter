package com.example.demo;

import com.umpay.online.tools.aspect.GenerateRpidHandler;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {


    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private GenerateRpidHandler generateRpidHandler;

    @Test
    void contextLoads() {
    }

}
