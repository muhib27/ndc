package com.classtune.ndc.apiresponse.reading_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RMResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RMData data;

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

    public RMData getData() {
        return data;
    }

    public void setData(RMData data) {
        this.data = data;
    }
}
