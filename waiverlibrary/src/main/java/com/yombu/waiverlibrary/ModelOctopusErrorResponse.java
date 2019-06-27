package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOctopusErrorResponse {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("dev_message")
    @Expose
    private String devMessage;
    @SerializedName("more_info")
    @Expose
    private String moreInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return "ModelOctopusErrorResponse{" +
                "code=" + code +
                ", devMessage='" + devMessage + '\'' +
                ", moreInfo='" + moreInfo + '\'' +
                '}';
    }
}
