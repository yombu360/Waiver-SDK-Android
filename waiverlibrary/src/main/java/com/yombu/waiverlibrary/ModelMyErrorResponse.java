package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class ModelMyErrorResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("errors")
    @Expose
    private List<ModelMyError> errors;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("result")
    @Expose
    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ModelMyError> getErrors() {
        return errors;
    }

    public void setErrors(List<ModelMyError> errors) {
        this.errors = errors;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ModelMyErrorResponse{" +
                "success=" + success +
                ", errors=" + errors +
                ", accessToken='" + accessToken + '\'' +
                ", result=" + result +
                '}';
    }
}
