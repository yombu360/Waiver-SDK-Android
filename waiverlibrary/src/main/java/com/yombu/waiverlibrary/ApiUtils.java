package com.yombu.waiverlibrary;

import android.content.Context;

class ApiUtils {

    private static volatile ServiceTerminalApi terminalApiService;
    private static volatile ServiceWaiverApi waiverApiService;
    private static volatile ServiceRegistrationApi registrationApi;

    private ApiUtils() {
    }

    static ServiceWaiverApi getWaiverApiService(Context context) {
        if (waiverApiService == null) {
            waiverApiService = RetrofitClient.getNewClientWithToken(Constants.BASE_URL, context).create(ServiceWaiverApi.class);
        }
        return waiverApiService;
    }

    static ServiceTerminalApi getTerminalApiService() {
        if (terminalApiService == null) {
            terminalApiService = RetrofitClient.getNewClient(Constants.BASE_URL_OCTOPUS).create(ServiceTerminalApi.class);
        }
        return terminalApiService;
    }

    static ServiceRegistrationApi getRegistrationApi(Context context) {
        if (registrationApi == null) {
            registrationApi = RetrofitClient.getNewClientWithToken(Constants.BASE_URL_OCTOPUS, context).create(ServiceRegistrationApi.class);
        }
        return registrationApi;
    }

}
