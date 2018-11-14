package com.classtune.ndc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("status")
    @Expose
    private String status;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
