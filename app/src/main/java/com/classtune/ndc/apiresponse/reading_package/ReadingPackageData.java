package com.classtune.ndc.apiresponse.reading_package;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadingPackageData {

    @SerializedName("reading_list")
    @Expose
    private List<ReadingList> readingList = null;

    public List<ReadingList> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<ReadingList> readingList) {
        this.readingList = readingList;
    }

}
