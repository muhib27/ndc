package com.classtune.ndc.apiresponse.CMBox;

import java.util.List;

import com.classtune.ndc.apiresponse.pigeonhole_api.SubmittedTask;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CMBoxData {

    @SerializedName("submitted_tasks")
    @Expose
    private List<CMBoxSubmittedTask> cmBoxSubmittedTasks = null;

    @SerializedName("submitted_task")
    @Expose
    private CMBoxSubmittedTask submittedTask;

    public List<CMBoxSubmittedTask> getCmBoxSubmittedTasks() {
        return cmBoxSubmittedTasks;
    }

    public void setCmBoxSubmittedTasks(List<CMBoxSubmittedTask> cmBoxSubmittedTasks) {
        this.cmBoxSubmittedTasks = cmBoxSubmittedTasks;
    }



    public CMBoxSubmittedTask getSubmittedTask() {
        return submittedTask;
    }

    public void setSubmittedTask(CMBoxSubmittedTask submittedTask) {
        this.submittedTask = submittedTask;
    }
}
