package com.classtune.ndc.apiresponse.course_calendar_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RoutineData routineData;

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

    public RoutineData getRoutineData() {
        return routineData;
    }

    public void setRoutineData(RoutineData routineData) {
        this.routineData = routineData;
    }
}