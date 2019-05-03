package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ModelCheckboxOption {

    @SerializedName("text")
    @Expose
    private ModelMyTextMap<String> text;
    @SerializedName("checked")
    @Expose
    private String checked;
    @SerializedName("required_text")
    @Expose
    private ModelMyTextMap<String> requiredText;

    public ModelMyTextMap<String> getText() {
        return text;
    }

    public void setText(ModelMyTextMap<String> text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked != null && checked.equals("1");
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public ModelMyTextMap<String> getRequiredText() {
        return requiredText;
    }

    public void setRequiredText(ModelMyTextMap<String> requiredText) {
        this.requiredText = requiredText;
    }

    @Override
    public String toString() {
        return "ModelCheckboxOption{" +
                "text=" + text +
                ", checked='" + checked + '\'' +
                ", requiredText=" + requiredText +
                '}';
    }

}
