package com.classtune.ndc.model;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PigeonholeDataModel implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("total")
    @Expose
    private String total;

    public PigeonholeDataModel() {
    }

    public PigeonholeDataModel(String status, String name, String id, String total) {
        this.status = status;
        this.name = name;
        this.id = id;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}