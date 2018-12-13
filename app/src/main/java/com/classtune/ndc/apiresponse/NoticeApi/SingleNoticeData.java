package com.classtune.ndc.apiresponse.NoticeApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SingleNoticeData {

    @SerializedName("notice")
    @Expose
    private Notice notice;
    @SerializedName("courses")
    @Expose
    private Object courses;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Object getCourses() {
        return courses;
    }

    public void setCourses(Object courses) {
        this.courses = courses;
    }

}