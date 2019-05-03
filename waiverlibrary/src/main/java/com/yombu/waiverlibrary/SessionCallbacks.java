package com.yombu.waiverlibrary;

import com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback;

class SessionCallbacks {

    private static volatile SessionCallbacks ourInstance = null;

    private YombuWaiverProcessingCallback waiverCallback;

    private SessionCallbacks() {
    }

    public static SessionCallbacks getInstance() {
        if (ourInstance == null) {
            ourInstance = new SessionCallbacks();
        }
        return ourInstance;
    }

    public YombuWaiverProcessingCallback getWaiverCallback() {
        return waiverCallback;
    }

    public void setWaiverCallback(YombuWaiverProcessingCallback waiverCallback) {
        this.waiverCallback = waiverCallback;
    }

    @Override
    public String toString() {
        return "SessionCallbacks{" +
                "waiverCallback=" + waiverCallback +
                '}';
    }
}
