package com.classtune.ndc.apiresponse.reading_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RMAttachment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("attachment_id")
    @Expose
    private String attachmentId;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("orig_name")
    @Expose
    private String origName;
    @SerializedName("file_ext")
    @Expose
    private String fileExt;
    @SerializedName("file_size")
    @Expose
    private String fileSize;
    @SerializedName("image_width")
    @Expose
    private String imageWidth;
    @SerializedName("image_height")
    @Expose
    private String imageHeight;
    @SerializedName("image_type")
    @Expose
    private String imageType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOrigName() {
        return origName;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}