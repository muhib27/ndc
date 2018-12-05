package com.classtune.ndc.model;

public class ClassScheduleModel {
    private String title;
    private String date;
    private String time;
    private String lecturerName;

    public ClassScheduleModel(String title, String date, String time, String lecturerName) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.lecturerName = lecturerName;
    }

    public ClassScheduleModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }
}
