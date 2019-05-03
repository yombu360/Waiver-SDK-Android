package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ModelWaiverTemplate {

    @SerializedName("waiver_template_id")
    @Expose
    private String waiverTemplateId;
    @SerializedName("body")
    @Expose
    private String body;

    public String getWaiverTemplateId() {
        return waiverTemplateId;
    }

    public void setWaiverTemplateId(String waiverTemplateId) {
        this.waiverTemplateId = waiverTemplateId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WaiverTemplateObject{" +
                "waiverTemplateId='" + waiverTemplateId + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
