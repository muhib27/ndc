package com.classtune.ndc.apiresponse.NoticeApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NoticeData noticeData;

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

    public NoticeData getNoticeData() {
        return noticeData;
    }

    public void setNoticeData(NoticeData noticeData) {
        this.noticeData = noticeData;
    }
}