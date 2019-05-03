package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class ModelMyResponse<T> {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("errors")
    @Expose
    private List<ModelMyError> errors;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("public_auth")
    @Expose
    private String publicAuth;
    @SerializedName("result")
    @Expose
    private T result;

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

    public String getPublicAuth() {
        return publicAuth;
    }

    public void setPublicAuth(String publicAuth) {
        this.publicAuth = publicAuth;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ModelMyResponse{" +
                "success=" + success +
                ", errors=" + errors +
                ", accessToken='" + accessToken + '\'' +
                ", publicAuth='" + publicAuth + '\'' +
                ", result=" + result +
                '}';
    }
}
