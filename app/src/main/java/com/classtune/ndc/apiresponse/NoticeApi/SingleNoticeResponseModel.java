package com.classtune.ndc.apiresponse.NoticeApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleNoticeResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private SingleNoticeData singleNoticeData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SingleNoticeData getSingleNoticeData() {
        return singleNoticeData;
    }

    public void setSingleNoticeData(SingleNoticeData singleNoticeData) {
        this.singleNoticeData = singleNoticeData;
    }
}