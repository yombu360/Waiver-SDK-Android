package com.yombu.waiverlibrary;

class Constants {

    // Back-end API Endpoints
    static final String BASE_URL_OCTOPUS = "https://octopus.api.yombu.com";
    static final String BASE_URL = "https://dev.api.pos.yombu.com";


    // Endpoints for making network calls
    static final String API_WAIVER_TEMPLATE = "/v5/store/{store_id}/waiver";
    static final String API_WAIVER_MINDBODY = "/v5/waiver:mindbody";
    static final String API_TERMINAL_INITIALIZATION = "/pos/v1/reseller/{rid}/device";


    // Date Time Parsing & Formatting Constants
    static final String DATE_FORMAT_FOR_WAIVER = "MMMM d, yyyy";


    // Storage Constants
    static final String SHARED_PREF_NAME = "yombu_shared_pref";
    static final String SHARED_PREF_DEVICE_CONFIG = "device_config";
    static final String SHARED_PREF_DEVICE_ID = "device_id";
    static final String SHARED_PREF_LAST_KEY_ROTATION_TIME = "last_key_rotation_time";
    static final String SHARED_PREF_SELECTED_LANGUAGE = "selected_language";


    // Replacement Fields
    static final String REPLACE_LOCATION_NAME = "{location_name}";
    static final String REPLACE_CUSTOMER_NAME = "{customer_name}";


    // Splitting Fields
    static final String SPLIT_INITIALS_BOX = "\\{\\{initials_box\\}\\}";


    // Languages Short Code
    static final String LANGUAGE_SHORT_CODE_ENGLISH = "en";


    // Intents Data Transfer Field Names
    static final String BACK = "BACK";


    // Notification Statuses
    static final String STATUS_NOTIFICATION_ENABLED = "1";
    static final String STATUS_NOTIFICATION_DISABLED = "-1";


    // Network Timeout Constants in seconds
    static final int CONNECTION_TIMEOUT = 30;
    static final int READ_TIMEOUT = 30;
    static final int WRITE_TIMEOUT = 30;


    // Product ID Constant
    static final String PRODUCT_GUEST_REGISTRATION_WAIVER = "3";


    // Date Time Parsing & Formatting Constants
    static final String DOB_FORMAT                       = "yyyy-MM-dd";
    static final String WAIVER_SCREEN_DATE_FORMAT        = "MMMM d, yyyy";


    // Genders
    static final String GENDER_MALE         = "male";
    static final String GENDER_FEMALE       = "female";
    static final String GENDER_OTHER        = "other";


    // Constant Public Key
    static final String PUBLIC_KEY          = "-----BEGIN PUBLIC KEY-----\n" +
            "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAEAd8r9D8itvfcILjYhN3T9r5XHYZvwODh\n" +
            "UE90gTlSCKoKSHmueOaWVSe/q1RZipkM/LmlCxaBOeQb9gHspVVWArZGq5PMILR8\n" +
            "BjKzEmG7u7o2411+HvcLze9j/Ap6yKU8\n" +
            "-----END PUBLIC KEY-----";
}
