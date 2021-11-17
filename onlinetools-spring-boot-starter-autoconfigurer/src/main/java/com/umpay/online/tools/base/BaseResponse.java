package com.umpay.online.tools.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umpay.online.tools.enums.RetMsgEnum;
import com.umpay.online.tools.enums.RetMsgEnumInterface;
import com.umpay.online.tools.util.DateUtil;

import java.time.LocalDateTime;

/**
 * @author tianxiaoyang
 * @Date: 2020/1/9 11:45
 * @Description:
 */
public class BaseResponse {
    /**
     * 请求id
     */
    private String rpid;
    /**
     * 返回码
     */
    private String retCode;
    /**
     * 返回语
     */
    private String retMsg;
    /**
     * 功能码，每个接口对应一个
     */
    private String funCode;

    /**
     * 返回日期
     */
    private String resDate;
    /**
     * 返回时间
     */
    private String resTime;


    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime;
    }

    public BaseResponse() {
    }

    public BaseResponse(String retCode, String retMsg){
        setRetCode(retCode);
        setRetMsg(retMsg);
        setResDateAndResTime();
    }

    @JsonIgnore
    public BaseResponse setRetCodeAndRetMsg(String retCode, String retMsg){
        setRetCode(retCode);
        setRetMsg(retMsg);
        setResDateAndResTime();
        return this;
    }

    @JsonIgnore
    public BaseResponse setRetMsgEnum(String retCode, String retMsg){
        setRetCode(retCode);
        setRetMsg(retMsg);
        setResDateAndResTime();
        return this;
    }

    @JsonIgnore
    public BaseResponse setRetMsgEnum(RetMsgEnumInterface retMsgEnum){
        setRetCode(retMsgEnum.getRetCode());
        setRetMsg(retMsgEnum.getRetMsg());
        setResDateAndResTime();
        return this;
    }

    @JsonIgnore
    public void setResDateAndResTime(LocalDateTime date){
        setResDate(DateUtil.dateFormatYYYYMMDD(date));
        setResTime(DateUtil.timeFormatHHMMSS(date));
    }
    @JsonIgnore
    public void setResDateAndResTime(){
        setResDate(DateUtil.dateFormatYYYYMMDD());
        setResTime(DateUtil.timeFormatHHMMSS());
    }

    public BaseResponse(RetMsgEnumInterface retMsgEnum) {
        setRetCode(retMsgEnum.getRetCode());
        setRetMsg(retMsgEnum.getRetMsg());
    }


    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
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

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public static BaseResponse ok(){
        return new BaseResponse(RetMsgEnum.SUCCESS);
    }
    //默认失败返回码
    public static BaseResponse fail(){
        return new BaseResponse(RetMsgEnum.FAIL);
    }
    public static BaseResponse create(String retCode, String retMsg){
        return new BaseResponse(retCode,retMsg);
    }
    public static BaseResponse create(RetMsgEnumInterface retMsgEnum){
        return new BaseResponse(retMsgEnum);
    }

    /**
     * @author : gaozhiguo
     * @date : 2020-05-14 14:08
     * @version : V1.0
     * @description : 校验响应结果
     **/
    public static Boolean checkResult(BaseResponse response){
        return response != null && "0000".equals(response.getRetCode());
    }
}
