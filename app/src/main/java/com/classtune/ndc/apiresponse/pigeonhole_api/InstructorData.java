package com.classtune.ndc.apiresponse.pigeonhole_api;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstructorData {

    @SerializedName("instructors")
    @Expose
    private List<Student> instructors = null;

    public List<Student> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Student> instructors) {
        this.instructors = instructors;
    }
}