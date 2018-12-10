package com.classtune.ndc.apiresponse.course_calendar_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Routine {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("sessionname")
    @Expose
    private String sessionname;
    @SerializedName("program_plan")
    @Expose
    private String programPlan;
    @SerializedName("show_in_routine")
    @Expose
    private String showInRoutine;
    @SerializedName("phasename")
    @Expose
    private String phasename;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSessionname() {
        return sessionname;
    }

    public void setSessionname(String sessionname) {
        this.sessionname = sessionname;
    }

    public String getProgramPlan() {
        return programPlan;
    }

    public void setProgramPlan(String programPlan) {
        this.programPlan = programPlan;
    }

    public String getShowInRoutine() {
        return showInRoutine;
    }

    public void setShowInRoutine(String showInRoutine) {
        this.showInRoutine = showInRoutine;
    }

    public String getPhasename() {
        return phasename;
    }

    public void setPhasename(String phasename) {
        this.phasename = phasename;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}