package com.umpay.online.tools.enums;

/**
 * @author tianxiaoyang
 * @Date: 2020/7/23 14:04
 * @Description: Enum没有办法继承只能通过接口实现来传递code和msg，
 * 依赖应用的Enum实现这个接口并返回响应的code和msg.
 */
public interface RetMsgEnumInterface {
    /**
     * 返回码
     * @return
     */
    String getRetCode();
    /**
     * 返回语
     * @return
     */
    String getRetMsg();
}
