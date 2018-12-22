package com.classtune.ndc.apiresponse.reading_package;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadingContent {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("type_ext")
    @Expose
    private String typeExt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("editable")
    @Expose
    private Boolean editable;
    @SerializedName("num_items")
    @Expose
    private Integer numItems;
    @SerializedName("num_shared")
    @Expose
    private Integer numShared;
    @SerializedName("content_details")
    @Expose
    private ContentDetails contentDetails;
    @SerializedName("attachment_id")
    @Expose
    private String attachmentId;
    @SerializedName("attachments")
    @Expose
    private List<RMAttachment> attachments = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeExt() {
        return typeExt;
    }

    public void setTypeExt(String typeExt) {
        this.typeExt = typeExt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Integer getNumItems() {
        return numItems;
    }

    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
    }

    public Integer getNumShared() {
        return numShared;
    }

    public void setNumShared(Integer numShared) {
        this.numShared = numShared;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public List<RMAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<RMAttachment> attachments) {
        this.attachments = attachments;
    }
}
