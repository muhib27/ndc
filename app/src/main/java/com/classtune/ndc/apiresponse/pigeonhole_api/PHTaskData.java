package com.classtune.ndc.apiresponse.pigeonhole_api;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PHTaskData {

    @SerializedName("tasks")
    @Expose
    private List<PHTask> phTasks = null;

    public List<PHTask> getPhTasks() {
        return phTasks;
    }

    public void setPhTasks(List<PHTask> phTasks) {
        this.phTasks = phTasks;
    }
}