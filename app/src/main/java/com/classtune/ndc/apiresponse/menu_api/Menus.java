package com.classtune.ndc.apiresponse.menu_api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menus {

    @SerializedName("main_menu")
    @Expose
    private List<String> mainMenu = null;
    @SerializedName("sub_menu")
    @Expose
    private List<String> subMenu = null;

    public List<String> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<String> mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<String> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<String> subMenu) {
        this.subMenu = subMenu;
    }

}