package com.classtune.ndc.apiresponse.pigeonhole_api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubmittedTaskData implements Serializable {

    @SerializedName("submitted_task")
    @Expose
    private SubmittedTask submittedTask;

    public SubmittedTask getSubmittedTask() {
        return submittedTask;
    }

    public void setSubmittedTask(SubmittedTask submittedTask) {
        this.submittedTask = submittedTask;
    }
}