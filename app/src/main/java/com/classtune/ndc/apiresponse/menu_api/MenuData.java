package com.classtune.ndc.apiresponse.menu_api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuData {

    @SerializedName("menus")
    @Expose
    private Menus menus;
    @SerializedName("user")
    @Expose
    private User user;

    public Menus getMenus() {
        return menus;
    }

    public void setMenus(Menus menus) {
        this.menus = menus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
