package com.classtune.ndc.apiresponse.profile_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresentAddress {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("road")
    @Expose
    private String road;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("telephone_no")
    @Expose
    private String telephoneNo;
    @SerializedName("mobile_1")
    @Expose
    private String mobile1;
    @SerializedName("mobile_2")
    @Expose
    private String mobile2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

}