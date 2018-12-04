package com.classtune.ndc.apiresponse.pigeonhole_api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PHTaskListResponse {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PHTaskData phTaskData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PHTaskData getPhTaskData() {
        return phTaskData;
    }

    public void setPhTaskData(PHTaskData phTaskData) {
        this.phTaskData = phTaskData;
    }
}
