package com.classtune.ndc.apiresponse.research_api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResearchWingData {

    @SerializedName("research")
    @Expose
    private List<Research> research = null;

    public List<Research> getResearch() {
        return research;
    }

    public void setResearch(List<Research> research) {
        this.research = research;
    }

}