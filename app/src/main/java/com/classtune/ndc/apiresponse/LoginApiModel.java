package com.classtune.ndc.apiresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginApiModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ApiKeyModel data;

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

    public ApiKeyModel getData() {
        return data;
    }

    public void setData(ApiKeyModel data) {
        this.data = data;
    }

}