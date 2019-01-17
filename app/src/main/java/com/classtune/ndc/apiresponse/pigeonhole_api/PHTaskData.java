package com.classtune.ndc.apiresponse.pigeonhole_api;


import java.util.List;

import com.classtune.ndc.model.MyAssignment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PHTaskData {

    @SerializedName("tasks")
    @Expose
    private List<PHTask> phTasks = null;

    @SerializedName("my_assignments")
    @Expose
    private List<MyAssignment> myAssignments = null;

    public List<PHTask> getPhTasks() {
        return phTasks;
    }

    public void setPhTasks(List<PHTask> phTasks) {
        this.phTasks = phTasks;
    }

    public List<MyAssignment> getMyAssignments() {
        return myAssignments;
    }

    public void setMyAssignments(List<MyAssignment> myAssignments) {
        this.myAssignments = myAssignments;
    }
}