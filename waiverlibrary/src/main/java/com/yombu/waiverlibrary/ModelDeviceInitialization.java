package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ModelDeviceInitialization {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("config")
    @Expose
    private ModelConfig config;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public ModelConfig getConfig() {
        return config;
    }

    public void setConfig(ModelConfig config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "ModelDeviceInitialization{" +
                "deviceId='" + deviceId + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", confirmation='" + confirmation + '\'' +
                ", config=" + config +
                '}';
    }
}
