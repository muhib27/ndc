package com.classtune.ndc.apiresponse.menu_api;

/**
 * Created by Muhib on 12/4/2018.
 */

public class UserMenu {
    public boolean tasks;
    public boolean events;
    public boolean routine;
    public boolean research;
    public boolean research_wing;
    public boolean reading_list;
    public boolean notice;


    public UserMenu() {
    }

    public boolean isTasks() {
        return tasks;
    }

    public void setTasks(boolean tasks) {
        this.tasks = tasks;
    }

    public boolean isEvents() {
        return events;
    }

    public void setEvents(boolean events) {
        this.events = events;
    }

    public boolean isResearch_wing() {
        return research_wing;
    }

    public void setResearch_wing(boolean research_wing) {
        this.research_wing = research_wing;
    }

    public boolean isReading_list() {
        return reading_list;
    }

    public void setReading_list(boolean reading_list) {
        this.reading_list = reading_list;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public boolean isRoutine() {
        return routine;
    }

    public void setRoutine(boolean routine) {
        this.routine = routine;
    }

    public boolean isResearch() {
        return research;
    }

    public void setResearch(boolean research) {
        this.research = research;
    }

}
