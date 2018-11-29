package com.classtune.ndc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhib on 27-Nov-18.
 */

public class AttachmentModel {
  private String fileName;
  private String filePath;

    public AttachmentModel(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public AttachmentModel() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
