package com.yombu.waiverlibrary.callbacks;

public interface YombuWaiverProcessingCallback {

    void onWaiverSuccess();

    void onWaiverFailure(String message);

}
