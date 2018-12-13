package com.classtune.ndc.apiresponse.NoticeApi;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeData {

    @SerializedName("notice")
    @Expose
    private List<Notice> notice = null;
    @SerializedName("courses")
    @Expose
    private Object courses;

    public List<Notice> getNotice() {
        return notice;
    }

    public void setNotice(List<Notice> notice) {
        this.notice = notice;
    }

}