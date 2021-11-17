package com.umpay.online.tools.base;

import com.umpay.online.tools.enums.RetMsgEnumInterface;
import org.springframework.beans.BeanUtils;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/16 11:28
 * @Description:基础公共信息
 */
public class BaseContext<T extends  BaseRequest,M extends BaseResponse> {

    public BaseContext() {
    }
    public BaseContext(T requestParam, M responseParam) {
        this.requestParam = requestParam;
        this.responseParam = responseParam;
        BeanUtils.copyProperties(requestParam, responseParam);
    }

    /**
     * 请求对象
     */
    private T requestParam;
    /**
     * 响应对象
     */
    private M responseParam;


    public T getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(T requestParam) {
        this.requestParam = requestParam;
    }

    public M getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(M responseParam) {
        this.responseParam = responseParam;
    }

    /**
     * @Description: 初始化BaseContext，并copy字段
     * @Param: [request, response]
     * @Author: xiaoyang
     * @Date: 2019-05-06 18:36
     */
    public static <T extends  BaseRequest,M extends BaseResponse> BaseContext<T,M> initContext(BaseRequest request, BaseResponse response) {
        //1.copy字段
        BeanUtils.copyProperties(request, response);
        //2.创建系统上下文
        BaseContext<T,M> context = new BaseContext<>();
        //3.将前置请求参数设置到上下文中 */
        context.setRequestParam((T) request);
        //4.将响应对象设置到上下文中 */
        context.setResponseParam((M) response);
        return context;
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-13 11:09
     * @version : V1.0
     * @description : 设置返回信息
     **/
    public BaseResponse setRetMsgEnum(RetMsgEnumInterface retMsgEnum) {
        return responseParam.setRetMsgEnum(retMsgEnum);
    }
}
