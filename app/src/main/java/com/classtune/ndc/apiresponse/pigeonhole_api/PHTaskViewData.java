package com.classtune.ndc.apiresponse.pigeonhole_api;

import java.util.List;

import com.classtune.ndc.apiresponse.Course;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PHTaskViewData {

    @SerializedName("task")
    @Expose
    private PHSingleTask phSingleTask;
    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public PHSingleTask getPhSingleTask() {
        return phSingleTask;
    }

    public void setPhSingleTask(PHSingleTask phSingleTask) {
        this.phSingleTask = phSingleTask;
    }
}