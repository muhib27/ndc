package com.classtune.ndc.apiresponse.research_api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResearchTopic {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("subhead")
    @Expose
    private String subhead;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("advisor")
    @Expose
    private String advisor;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("is_locked")
    @Expose
    private String isLocked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

}
