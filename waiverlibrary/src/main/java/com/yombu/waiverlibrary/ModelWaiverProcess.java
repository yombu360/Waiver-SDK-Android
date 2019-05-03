package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class ModelWaiverProcess {

    @SerializedName("waiver_id")
    @Expose
    private String waiverId;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;
    @SerializedName("waiver_template_id")
    @Expose
    private String waiverTemplateId;
    @SerializedName("minor_list")
    @Expose
    private List<ModelWaiverMinor> waiverMinors;

    public String getWaiverId() {
        return waiverId;
    }

    public void setWaiverId(String waiverId) {
        this.waiverId = waiverId;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getWaiverTemplateId() {
        return waiverTemplateId;
    }

    public void setWaiverTemplateId(String waiverTemplateId) {
        this.waiverTemplateId = waiverTemplateId;
    }

    public List<ModelWaiverMinor> getWaiverMinors() {
        return waiverMinors;
    }

    public void setWaiverMinors(List<ModelWaiverMinor> waiverMinors) {
        this.waiverMinors = waiverMinors;
    }

    @Override
    public String toString() {
        return "WaiverProcessObject{" +
                "waiverId='" + waiverId + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                ", waiverTemplateId='" + waiverTemplateId + '\'' +
                ", waiverMinors=" + waiverMinors +
                '}';
    }

}
