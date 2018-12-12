package com.classtune.ndc.apiresponse.course_calendar_api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsData {

    @SerializedName("event_list")
    @Expose
    private List<EventList> eventList = null;

    public List<EventList> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventList> eventList) {
        this.eventList = eventList;
    }

}