package com.classtune.ndc.apiresponse.course_calendar_api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineData {

    @SerializedName("routine")
    @Expose
    private List<Routine> routine = null;

    public List<Routine> getRoutine() {
        return routine;
    }

    public void setRoutine(List<Routine> routine) {
        this.routine = routine;
    }

}