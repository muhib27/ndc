package com.classtune.ndc.apiresponse.pigeonhole_api;

import java.util.List;

import com.classtune.ndc.apiresponse.Attachment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PHSingleTask {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("submitted_total")
    @Expose
    private String submittedTotal;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;
    @SerializedName("course")
    @Expose
    private List<String> course = null;
    @SerializedName("users")
    @Expose
    private List<String> users = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubmittedTotal() {
        return submittedTotal;
    }

    public void setSubmittedTotal(String submittedTotal) {
        this.submittedTotal = submittedTotal;
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

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

}