package com.yombu.waiverlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yombu.waiverlibrary.callbacks.YombuInitializationCallback;
import com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YombuWaiver {

    private static YombuWaiver myInstance;
    private static Context myContext;

    private YombuWaiver() {
    }

    public static YombuWaiver getInstance() {
        if (myInstance == null) {
            myInstance = new YombuWaiver();
        }
        return myInstance;
    }

    static void init(Context context) {
        myContext = context;
    }

    public boolean isInitialized() {
        return Terminal.getInstance(myContext).isRegistered();
    }

    public YombuWaiver initialize(String resellerId, String apiKey, String merchantId, String locationId, final YombuInitializationCallback initializationCallback) {
        final Terminal terminal = Terminal.getInstance(myContext);
        ApiUtils.getTerminalApiService().initialize(
                resellerId,
                terminal.getMacAddress(),
                Constants.PUBLIC_KEY,
                LibraryAndroid.getSerialNumber(),
                Constants.PRODUCT_GUEST_REGISTRATION_WAIVER,
                merchantId,
                apiKey,
                locationId
        ).enqueue(new Callback<ModelDeviceInitialization>() {
            @Override
            public void onResponse(@NonNull Call<ModelDeviceInitialization> call, @NonNull Response<ModelDeviceInitialization> response) {
                ModelDeviceInitialization deviceInitialization = response.body();

                if (response.isSuccessful() && deviceInitialization != null) {

                    terminal.setDeviceId(myContext, deviceInitialization.getDeviceId());
                    terminal.setDeviceConfig(myContext, deviceInitialization.getConfig());
                    terminal.setAccessToken(myContext, response.headers().get("Access-Token"));

                    initializationCallback.onInitializationSuccess();

                } else {

                    ModelOctopusErrorResponse errorResponse = null;
                    try {
                        errorResponse = LibraryResponseConverter.convertToOctopusErrorResponse(response.errorBody());
                    } catch (Exception ex) {
                    }
                    String errorMessage = myContext.getString(R.string.unable_to_register_terminal);
                    if (errorResponse != null) {
                        errorMessage = errorResponse.getDevMessage();
                    }

                    initializationCallback.onInitializationFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelDeviceInitialization> call, @NonNull Throwable t) {
                initializationCallback.onInitializationFailure("Network Failure: " + t.getMessage());
            }
        });
        return myInstance;
    }

    public YombuWaiver displayWaiver(YombuWaiverProcessingCallback waiverCallback) {
        if (isInitialized()) {
            SessionCallbacks.getInstance().setWaiverCallback(waiverCallback);
            Intent waiverActivityIntent = new Intent(myContext, ActivityWaiver.class);
            waiverActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myContext.startActivity(waiverActivityIntent);
        } else {
            waiverCallback.onWaiverFailure("This app is not initialized!");
        }
        return myInstance;
    }

    public YombuWaiver addString(String key, String value) {
        String string = null;
        if (value != null) {
            string = value.trim();
        }
        Session.getInstance().addUserData(key, string);
        return myInstance;
    }

    public YombuWaiver addDate(String key, Date value) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DOB_FORMAT, Locale.US);
        String birthday = formatter.format(value);
        Session.getInstance().addUserData(key, birthday);
        return myInstance;
    }

    public YombuWaiver addGender(String key, Gender value) {
        String gender = null;
        switch (value) {
            case MALE:
                gender = Constants.GENDER_MALE;
                break;

            case FEMALE:
                gender = Constants.GENDER_FEMALE;
                break;

            case OTHER:
                gender = Constants.GENDER_OTHER;
                break;
        }
        Session.getInstance().addUserData(key, gender);
        return myInstance;
    }

    public YombuWaiver addList(String key, List<String> value) {
        String string = null;
        if (value.size() > 0) {
            string = TextUtils.join("||", value);
        }
        Session.getInstance().addUserData(key, string);
        return myInstance;
    }

    public YombuWaiver addMinorsList(List<Minor> minors) {
        if (minors != null) {
            Session.getInstance().setMinors(minors);
        }
        return myInstance;
    }

    public YombuWaiver addMinor(Minor minor) {
        Session.getInstance().addMinor(minor);
        return myInstance;
    }

    public YombuWaiver clearMinors() {
        Session.getInstance().clearMinors();
        return myInstance;
    }

    public YombuWaiver clearAllData() {
        Session.getInstance().reset();
        return myInstance;
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public class Keys {
        // Constants Network API Keys
        public static final String MINDBODY_ID = "mindbody_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String BIRTHDAY = "birthday";
        public static final String GENDER = "gender";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String ADDRESS = "address";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String ZIP = "zip";
        public static final String COUNTRY = "country";
        public static final String GUARDIAN_FIRST_NAME = "guardian_first_name";
        public static final String GUARDIAN_LAST_NAME = "guardian_last_name";
        public static final String EMERGENCY_CONTACT_FIRST_NAME = "emergency_contact_first_name";
        public static final String EMERGENCY_CONTACT_LAST_NAME = "emergency_contact_last_name";
        public static final String EMERGENCY_CONTACT_PHONE = "emergency_contact_phone";
        public static final String HEAR_ABOUT_US = "hear_about_us";
        public static final String REFERRAL = "referral";
    }

}
