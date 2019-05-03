package com.yombu.waiverlibrary.callbacks;

public interface YombuInitializationCallback {

    void onInitializationSuccess();

    void onInitializationFailure(String message);

}
