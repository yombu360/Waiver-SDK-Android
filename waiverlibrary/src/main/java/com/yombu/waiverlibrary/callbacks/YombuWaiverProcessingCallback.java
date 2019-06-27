package com.yombu.waiverlibrary.callbacks;

public interface YombuWaiverProcessingCallback {

    void onWaiverSuccess(String documentId);

    void onWaiverFailure(String message);

}
