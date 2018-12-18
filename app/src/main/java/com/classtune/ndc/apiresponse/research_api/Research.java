package com.classtune.ndc.apiresponse.research_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Research {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("batch_name")
    @Expose
    private String batchName;
    @SerializedName("rank_name")
    @Expose
    private String rankName;
    @SerializedName("service_number")
    @Expose
    private String serviceNumber;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("research_count")
    @Expose
    private String researchCount;
    @SerializedName("is_research_lock")
    @Expose
    private String isResearchLock;

    public Research() {
    }

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

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getResearchCount() {
        return researchCount;
    }

    public void setResearchCount(String researchCount) {
        this.researchCount = researchCount;
    }

    public String getIsResearchLock() {
        return isResearchLock;
    }

    public void setIsResearchLock(String isResearchLock) {
        this.isResearchLock = isResearchLock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}