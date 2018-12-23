package com.classtune.ndc.apiresponse.profile_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("permanent_address")
    @Expose
    private Object permanentAddress;
    @SerializedName("present_address")
    @Expose
    private Object presentAddress;

    public Object getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(Object permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Object getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(Object presentAddress) {
        this.presentAddress = presentAddress;
    }

}