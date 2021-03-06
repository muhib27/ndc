package com.classtune.ndc.apiresponse.research_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResearchTopicResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ResearchTopicData researchTopicData;

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

    public ResearchTopicData getResearchTopicData() {
        return researchTopicData;
    }

    public void setResearchTopicData(ResearchTopicData researchTopicData) {
        this.researchTopicData = researchTopicData;
    }
}
