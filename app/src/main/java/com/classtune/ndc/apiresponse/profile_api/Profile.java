package com.classtune.ndc.apiresponse.profile_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("user_data")
    @Expose
    private UserProfileData userProfileData;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("address")
    @Expose
    private Address address;

    public UserProfileData getUserProfileData() {
        return userProfileData;
    }

    public void setUserProfileData(UserProfileData userProfileData) {
        this.userProfileData = userProfileData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}