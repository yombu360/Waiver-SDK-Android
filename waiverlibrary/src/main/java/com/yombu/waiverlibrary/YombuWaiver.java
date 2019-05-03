package com.yombu.waiverlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.yombu.waiverlibrary.callbacks.YombuInitializationCallback;
import com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback;

import java.util.Date;
import java.util.List;

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

                    ModelMyErrorResponse errorResponse = null;
                    try {
                        errorResponse = LibraryResponseConverter.convertToErrorResponse(response.errorBody());
                    } catch (Exception ex) {
                    }

                    String errorMessage = myContext.getString(R.string.unable_to_register_terminal);
                    if (errorResponse != null && !errorResponse.getErrors().isEmpty()) {
                        errorMessage = errorResponse.getErrors().get(0).getMessage();
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
            myContext.startActivity(new Intent(myContext, ActivityWaiver.class));
        } else {
            waiverCallback.onWaiverFailure("This app is not initialized!");
        }
        return myInstance;
    }

    public YombuWaiver setMindbodyId(String mindbodyId) {
        Session.getInstance().setMindbodyId(mindbodyId);
        return myInstance;
    }

    public YombuWaiver setName(String firstName, String lastName) {
        Session.getInstance().setFirstName(firstName);
        Session.getInstance().setLastName(lastName);
        return myInstance;
    }

    public YombuWaiver setPhone(String phone) {
        Session.getInstance().setPhone(phone);
        return myInstance;
    }

    public YombuWaiver setEmail(String email) {
        Session.getInstance().setEmail(email);
        return myInstance;
    }

    public YombuWaiver setDateOfBirth(Date dateOfBirth) {
        Session.getInstance().setBirthday(dateOfBirth);
        return myInstance;
    }

    public YombuWaiver setGender(Gender gender) {
        switch (gender) {
            case MALE:
                Session.getInstance().setGender(Constants.GENDER_MALE);
                break;

            case FEMALE:
                Session.getInstance().setGender(Constants.GENDER_FEMALE);
                break;

            case OTHER:
                Session.getInstance().setGender(Constants.GENDER_OTHER);
                break;
        }
        return myInstance;
    }

    public YombuWaiver setCiNumber(String ciNumber) {
        Session.getInstance().setCiNumber(ciNumber);
        return myInstance;
    }

    public YombuWaiver setUsernamePassword(String username, String password) {
        Session.getInstance().setUsername(username);
        Session.getInstance().setPassword(password);
        return myInstance;
    }

    public YombuWaiver setAddress(String address, String city, String state, String postalCode, String countryShortCode) {
        Session.getInstance().setAddress(address);
        Session.getInstance().setCity(city);
        Session.getInstance().setState(state);
        Session.getInstance().setZip(postalCode);
        Session.getInstance().setCountry(countryShortCode);
        return myInstance;
    }

    public YombuWaiver setGuardianName(String guardianFirstName, String guardianLastName) {
        Session.getInstance().setGuardianFirstName(guardianFirstName);
        Session.getInstance().setGuardianLastName(guardianLastName);
        return myInstance;
    }

    public YombuWaiver setEmergencyContact(String emergencyContactFirstName, String emergencyContactLastName, String emergencyContactPhone) {
        Session.getInstance().setEmergencyContactFirstName(emergencyContactFirstName);
        Session.getInstance().setEmergencyContactLastName(emergencyContactLastName);
        Session.getInstance().setEmergencyContactPhone(emergencyContactPhone);
        return myInstance;
    }

    public YombuWaiver addMinor(String minorFirstName, String minorLastName, Date minorDateOfBirth) {
        Session.getInstance().addMinor(minorFirstName, minorLastName, minorDateOfBirth);
        return myInstance;
    }

    public YombuWaiver setHearAboutUsOptions(List<String> hearAboutUsOptions) {
        Session.getInstance().setHearAboutUsOptions(hearAboutUsOptions);
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

}
