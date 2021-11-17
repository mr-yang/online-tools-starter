package com.umpay.online.tools.exception;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umpay.online.tools.base.BaseResponse;
import com.umpay.online.tools.enums.RetMsgEnumInterface;
import com.umpay.online.tools.util.LoggerTools;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/9 09:46
 * @Description:自定异常
 */
public class OnlineException extends RuntimeException {


    private BaseResponse data;

    public BaseResponse getData() {
        return data;
    }

    public void setData(BaseResponse data) {
        this.data = data;
    }

    public OnlineException() {}

    public OnlineException(BaseResponse data) {
        setData(data);
    }

    public OnlineException(String retCode, String retMsg) {
        this.data = new BaseResponse(retCode,retMsg);
    }

    public OnlineException(RetMsgEnumInterface retMsgEnum) {
        if (retMsgEnum != null) {
            this.data = new BaseResponse(retMsgEnum.getRetCode(),retMsgEnum.getRetMsg());
        }
    }

    public static void cast(RetMsgEnumInterface retMsgEnum) {
        throw new OnlineException(retMsgEnum);
    }
    public static void cast(String retCode, String retMsg) {
        throw new OnlineException(retCode, retMsg);
    }
    public static void cast(BaseResponse response) {
        throw new OnlineException(response);
    }
    public static void cast(Exception e, BaseResponse responseParam, RetMsgEnumInterface retMsgEnum) {
        if(e instanceof OnlineException){
            throw (OnlineException)e;
        }else{
            LoggerTools.error(retMsgEnum.getRetMsg(), e);
            cast(responseParam.setRetMsgEnum(retMsgEnum));
        }
    }

    /**
     * 获取自定义异常返回的返回码和返回语，打印简要日志使用
     * @return
     */
    public JSONObject getJSONObjectData(){
        BaseResponse data = getData();
        if (data != null) {
            return JSONObject.parseObject(JSON.toJSONString(data));
        }
        return new JSONObject();
    }
}
