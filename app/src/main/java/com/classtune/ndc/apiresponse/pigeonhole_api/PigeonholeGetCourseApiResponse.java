package com.classtune.ndc.apiresponse.pigeonhole_api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PigeonholeGetCourseApiResponse {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private GetCourseData CourseData;

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

    public GetCourseData getCourseData() {
        return CourseData;
    }

    public void setCourseData(GetCourseData courseData) {
        CourseData = courseData;
    }
}
