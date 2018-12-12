package com.classtune.ndc.apiresponse.course_calendar_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private EventsData eventsData;

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

    public EventsData getEventsData() {
        return eventsData;
    }

    public void setEventsData(EventsData eventsData) {
        this.eventsData = eventsData;
    }
}