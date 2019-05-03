package com.yombu.waiverlibrary;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

class ModelConfig {

    @SerializedName("waiver_checkbox_options")
    @Expose
    private String waiverCheckboxOptions;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("email_subscription_checkbox_txt")
    @Expose
    private String emailSubscriptionCheckboxText;
    @SerializedName("override_waiver_accept_title_txt")
    @Expose
    private String overrideWaiverAcceptTitleText;
    @SerializedName("override_signature_consent_txt")
    @Expose
    private String overrideSignatureConsentText;

    public List<ModelCheckboxOption> getWaiverCheckboxOptions() {
        List<ModelCheckboxOption> result = new Gson().fromJson(waiverCheckboxOptions, new TypeToken<List<ModelCheckboxOption>>() {
        }.getType());
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public void setWaiverCheckboxOptions(String waiverCheckboxOptions) {
        this.waiverCheckboxOptions = waiverCheckboxOptions;
    }

    public ModelMyTextMap<String> getLocationName() {
        ModelMyTextMap<String> result = new Gson().fromJson(locationName, new TypeToken<ModelMyTextMap<String>>() {
        }.getType());
        if (result == null) {
            result = new ModelMyTextMap<>();
        }
        return result;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public ModelMyTextMap<String> getEmailSubscriptionCheckboxText() {
        ModelMyTextMap<String> result = new Gson().fromJson(emailSubscriptionCheckboxText, new TypeToken<ModelMyTextMap<String>>() {
        }.getType());
        if (result == null) {
            result = new ModelMyTextMap<>();
        }
        return result;
    }

    public void setEmailSubscriptionCheckboxText(String emailSubscriptionCheckboxText) {
        this.emailSubscriptionCheckboxText = emailSubscriptionCheckboxText;
    }

    public ModelMyTextMap<String> getOverrideWaiverAcceptTitleText() {
        ModelMyTextMap<String> result = new Gson().fromJson(overrideWaiverAcceptTitleText, new TypeToken<ModelMyTextMap<String>>() {
        }.getType());
        if (result == null) {
            result = new ModelMyTextMap<>();
        }
        return result;
    }

    public void setOverrideWaiverAcceptTitleText(String overrideWaiverAcceptTitleText) {
        this.overrideWaiverAcceptTitleText = overrideWaiverAcceptTitleText;
    }

    public ModelMyTextMap<String> getOverrideSignatureConsentText() {
        ModelMyTextMap<String> result = new Gson().fromJson(overrideSignatureConsentText, new TypeToken<ModelMyTextMap<String>>() {
        }.getType());
        if (result == null) {
            result = new ModelMyTextMap<>();
        }
        return result;
    }

    public void setOverrideSignatureConsentText(String overrideSignatureConsentText) {
        this.overrideSignatureConsentText = overrideSignatureConsentText;
    }

    @Override
    public String toString() {
        return "ModelConfig{" +
                "waiverCheckboxOptions='" + waiverCheckboxOptions + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", emailSubscriptionCheckboxText='" + emailSubscriptionCheckboxText + '\'' +
                ", overrideWaiverAcceptTitleText='" + overrideWaiverAcceptTitleText + '\'' +
                ", overrideSignatureConsentText='" + overrideSignatureConsentText + '\'' +
                '}';
    }
}
