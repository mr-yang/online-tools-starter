package com.umpay.online.tools.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tianxiaoyang
 * @Date: 2020-05-27 09:38
 * @Description: SpringBoot使用Jackson，全局反序列化去除字符串前后空格
 */
@Component
public class StringTrimModule extends SimpleModule {

    public StringTrimModule() {
        addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt)
                    throws IOException {
                String value = p.getValueAsString();
                if(value != null){
                    return value.trim();
                }
                return null;
            }
        });
    }

}