package com.umpay.online.tools.util;

import org.apache.commons.lang3.StringUtils;

/**
* @Description: 拦截器更改线程名称
* @Author: xiaoyang
* @Date: 2020/1/15 10:13 上午
*/
public class ThreadChangeName {
    /**
    * @Description: 修改线程id，改良版，先判断rpid不为null在获取设置，并在判断里面try
    * @Param: [rpid]
    * @Author: xiaoyang
    * @Date: 2020/1/15 10:12 上午
    */
    public static void changeThreadName(String rpid) {
        if (!StringUtils.isEmpty(rpid)) {
            try {
                //获取线程
                Thread thread = Thread.currentThread();
                //更改线程名称
                thread.setName(rpid);
            } catch (Exception e) {
                LoggerTools.error("更改线程名称异常", e);
            }
        }
    }
}