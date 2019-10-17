package com.yombu.waiverlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Session {

    private static volatile Session ourInstance = null;

    private String waiverTemplateId;
    private String receiveEmailNotification;
    private String signature;

    private Map<String, String> userData;

    private List<Minor> minors;

    private Session() {
        minors = new ArrayList<>();
        userData = new HashMap<>();
        reset();
    }

    public static Session getInstance() {
        if (ourInstance == null) {
            ourInstance = new Session();
        }
        return ourInstance;
    }

    public void reset() {
        waiverTemplateId = null;
        receiveEmailNotification = Constants.STATUS_NOTIFICATION_ENABLED;
        signature = null;

        minors.clear();
        userData.clear();
    }

    public String getWaiverTemplateId() {
        return waiverTemplateId;
    }

    public void setWaiverTemplateId(String waiverTemplateId) {
        this.waiverTemplateId = waiverTemplateId;
    }

    public String getReceiveEmailNotification() {
        return receiveEmailNotification;
    }

    public void setReceiveEmailNotification(String receiveEmailNotification) {
        this.receiveEmailNotification = receiveEmailNotification;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFullName() {
        String fullName = "";
        if (userData.get(YombuWaiver.Keys.FIRST_NAME) != null) {
            fullName += userData.get(YombuWaiver.Keys.FIRST_NAME);
        }
        if (userData.get(YombuWaiver.Keys.LAST_NAME) != null) {
            if (fullName.isEmpty()) {
                fullName += userData.get(YombuWaiver.Keys.LAST_NAME);
            } else {
                fullName += " " + userData.get(YombuWaiver.Keys.LAST_NAME);
            }
        }
        return fullName;
    }

    public void addUserData(String key, String value) {
        userData.put(key, value);
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public void addMinor(Minor minor) {
        this.minors.add(minor);
    }

    public List<Minor> getMinors() {
        return minors;
    }

    public void setMinors(List<Minor> minors) {
        this.minors = minors;
    }

    public void clearMinors() {
        minors.clear();
    }

}
