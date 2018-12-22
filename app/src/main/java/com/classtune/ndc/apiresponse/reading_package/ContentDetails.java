package com.classtune.ndc.apiresponse.reading_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("attachment_id")
    @Expose
    private String attachmentId;
    @SerializedName("notify_email")
    @Expose
    private String notifyEmail;
    @SerializedName("notify_push")
    @Expose
    private String notifyPush;
    @SerializedName("sms_alert")
    @Expose
    private String smsAlert;
    @SerializedName("must_read")
    @Expose
    private String mustRead;
    @SerializedName("may_read")
    @Expose
    private String mayRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public String getNotifyPush() {
        return notifyPush;
    }

    public void setNotifyPush(String notifyPush) {
        this.notifyPush = notifyPush;
    }

    public String getSmsAlert() {
        return smsAlert;
    }

    public void setSmsAlert(String smsAlert) {
        this.smsAlert = smsAlert;
    }

    public String getMustRead() {
        return mustRead;
    }

    public void setMustRead(String mustRead) {
        this.mustRead = mustRead;
    }

    public String getMayRead() {
        return mayRead;
    }

    public void setMayRead(String mayRead) {
        this.mayRead = mayRead;
    }

}
