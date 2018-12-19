package com.classtune.ndc.apiresponse.pigeonhole_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstructorResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private InstructorData instructorData;

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

    public InstructorData getInstructorData() {
        return instructorData;
    }

    public void setInstructorData(InstructorData instructorData) {
        this.instructorData = instructorData;
    }
}