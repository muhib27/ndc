package com.classtune.ndc.apiresponse.pigeonhole_api;

import java.util.ArrayList;
import java.util.List;

public class PigeonholeTaskAdd {

    public String title;
    public String description;
    public String due_date;
    public ArrayList<String> courses = null;
    public ArrayList<String> users = null;
    public ArrayList<String> attachments = null;

    public PigeonholeTaskAdd() {
    }

    public PigeonholeTaskAdd(String title, String description, String due_date, ArrayList<String> courses, ArrayList<String> users, ArrayList<String> attachments) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.courses = courses;
        this.users = users;
        this.attachments = attachments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
    }
}
