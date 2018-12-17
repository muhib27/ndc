package com.classtune.ndc.apiresponse.research_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResearchWingResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ResearchWingData researchWingData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResearchWingData getResearchWingData() {
        return researchWingData;
    }

    public void setResearchWingData(ResearchWingData researchWingData) {
        this.researchWingData = researchWingData;
    }
}
