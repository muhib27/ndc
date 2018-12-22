package com.classtune.ndc.apiresponse.reading_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RMData {

    @SerializedName("reading_content")
    @Expose
    private ReadingContent readingContent;

    public ReadingContent getReadingContent() {
        return readingContent;
    }

    public void setReadingContent(ReadingContent readingContent) {
        this.readingContent = readingContent;
    }

}