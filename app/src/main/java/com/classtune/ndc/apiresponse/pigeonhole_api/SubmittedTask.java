package com.classtune.ndc.apiresponse.pigeonhole_api;

import java.util.List;

import com.classtune.ndc.apiresponse.Attachment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmittedTask {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("submitted_to")
    @Expose
    private String submittedTo;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("viewed_by_instructor")
    @Expose
    private String viewedByInstructor;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubmittedTo() {
        return submittedTo;
    }

    public void setSubmittedTo(String submittedTo) {
        this.submittedTo = submittedTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getViewedByInstructor() {
        return viewedByInstructor;
    }

    public void setViewedByInstructor(String viewedByInstructor) {
        this.viewedByInstructor = viewedByInstructor;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

}