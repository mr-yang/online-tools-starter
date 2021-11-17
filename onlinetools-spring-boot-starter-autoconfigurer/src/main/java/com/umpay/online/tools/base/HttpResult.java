package com.umpay.online.tools.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umpay.online.tools.enums.RetMsgEnum;
import com.umpay.online.tools.enums.RetMsgEnumInterface;
import org.apache.commons.lang3.StringUtils;


/**
 * @Auther: xiaoyang
 * @Date: 2020/1/8 10:31
 * @Description:请求第三方通用返回对象
 */
public class HttpResult<T> {
    /**
     * 返回码
     */
    private String retCode;
    /**
     * 返回语
     */
    private String retMsg;
    /**
     * 返回数据，可以为object和list，取决于泛型
     */
    private T data;
    
    public HttpResult(String retCode, String retMsg, T data) {
        setData(data);
        setRetCode(retCode);
        setRetMsg(retMsg);
    }
    public HttpResult(String retCode, String retMsg) {
        setRetCode(retCode);
        setRetMsg(retMsg);
    }
    public HttpResult(RetMsgEnumInterface retMsgEnum, T data) {
        setData(data);
        setRetCode(retMsgEnum.getRetCode());
        setRetMsg(retMsgEnum.getRetMsg());
    }
    public HttpResult(RetMsgEnumInterface retMsgEnum) {
        setRetCode(retMsgEnum.getRetCode());
        setRetMsg(retMsgEnum.getRetMsg());
    }

    //json序列化忽略此字段
    @JsonIgnore
    public boolean isSuccess(){
        return RetMsgEnum.SUCCESS.getRetCode().equals(getRetCode());
    }


    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public static <T> HttpResult<T> create(String retCode, String retMsg, T data) {
        return new HttpResult<>(retCode, retMsg, data);
    }

    public static <T> HttpResult<T> ok(T data) {
        return new HttpResult<>(RetMsgEnum.SUCCESS, data);
    }

    public static <T> HttpResult<T> create(RetMsgEnumInterface retMsgEnum, T data) {
        return new HttpResult<>(retMsgEnum, data);
    }

    public static <T> HttpResult<T> create(RetMsgEnumInterface retMsgEnum) {
        return new HttpResult<T>(retMsgEnum);
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-12 17:12
     * @version : V1.0
     * @description : 校验响应结果
     **/
    public Boolean checkHttpResult() {

        if (!RetMsgEnum.SUCCESS.getRetCode().equals(this.retCode)) {
            return false;
        }

        if (this.getData() == null) {
            return false;
        }

        BaseResponse baseResponse = (BaseResponse) this.getData();
        if (!RetMsgEnum.SUCCESS.getRetCode().equals(baseResponse.getRetCode())) {
            return false;
        }
        return true;
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-12 17:12
     * @version : V1.0
     * @description : 返回码幂等处理
     **/
    public Boolean checkHttpResult(String retCode) {
        if (!RetMsgEnum.SUCCESS.getRetCode().equals(this.retCode)) {
            return false;
        }

        if (this.getData() == null) {
            return false;
        }
        BaseResponse baseResponse = (BaseResponse) this.getData();
        if (!RetMsgEnum.SUCCESS.getRetCode().equals(baseResponse.getRetCode()) && (StringUtils.isEmpty(retCode) || !retCode.equals(baseResponse.getRetCode()))) {
            return false;
        }
        return true;
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-14 16:20
     * @version : V1.0
     * @description : 响应成功结果
     **/
    public <T> T httpResultSuccess() {
        if(this.data instanceof BaseResponse){
            BaseResponse response = (BaseResponse) this.data;
            response.setRetMsgEnum(RetMsgEnum.SUCCESS);
        }
        return (T) this.data;
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-12 17:12
     * @version : V1.0
     * @description : 指定返回吗返回语
     **/
    public <T> T httpResult(RetMsgEnumInterface coreRetMsgEnum) {
        if(this.data instanceof BaseResponse){
            BaseResponse response = (BaseResponse) this.data;
            response.setRetMsgEnum(coreRetMsgEnum);
            return (T) response;
        }
        return (T) this.data;
    }
}
