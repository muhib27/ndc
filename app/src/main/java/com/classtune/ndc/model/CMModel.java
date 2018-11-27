package com.classtune.ndc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhib on 27-Nov-18.
 */

public class CMModel {
    @SerializedName("cm_id")
    @Expose
    private String cm_id;

    @SerializedName("cm_name")
    @Expose
    private String cm_name;
    String defaulter;

    private boolean selected;

    public CMModel(String cm_id, String cm_name, String defaulter, boolean selected) {
        this.cm_id = cm_id;
        this.cm_name = cm_name;
        this.defaulter = defaulter;
        this.selected = selected;
    }

    public CMModel(String cm_id, String cm_name) {
        this.cm_id = cm_id;
        this.cm_name = cm_name;
    }

    public CMModel(String cm_id, String cm_name, String defaulter) {
        this.cm_id = cm_id;
        this.cm_name = cm_name;
        this.defaulter = defaulter;
    }

    public String getCm_id() {
        return cm_id;
    }

    public void setCm_id(String cm_id) {
        this.cm_id = cm_id;
    }

    public String getCm_name() {
        return cm_name;
    }

    public void setCm_name(String cm_name) {
        this.cm_name = cm_name;
    }

    public String getDefaulter() {
        return defaulter;
    }

    public void setDefaulter(String defaulter) {
        this.defaulter = defaulter;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
