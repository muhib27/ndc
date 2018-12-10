package com.classtune.ndc.apiresponse.CMBox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CMBoxSubmittedTaskResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private CMBoxData cmBoxData;

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

    public CMBoxData getCmBoxData() {
        return cmBoxData;
    }

    public void setCmBoxData(CMBoxData cmBoxData) {
        this.cmBoxData = cmBoxData;
    }
}
