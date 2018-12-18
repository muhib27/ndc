package com.classtune.ndc.apiresponse.research_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResearchTopicData {
    @SerializedName("research")
    @Expose
    private List<UserResearchTopic> userResearchTopics = null;

    public List<UserResearchTopic> getUserResearchTopics() {
        return userResearchTopics;
    }

    public void setUserResearchTopics(List<UserResearchTopic> userResearchTopics) {
        this.userResearchTopics = userResearchTopics;
    }
}
