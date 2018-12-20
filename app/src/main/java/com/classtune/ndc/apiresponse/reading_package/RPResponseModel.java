package com.classtune.ndc.apiresponse.reading_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RPResponseModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ReadingPackageData readingPackageData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReadingPackageData getReadingPackageData() {
        return readingPackageData;
    }

    public void setReadingPackageData(ReadingPackageData readingPackageData) {
        this.readingPackageData = readingPackageData;
    }
}
