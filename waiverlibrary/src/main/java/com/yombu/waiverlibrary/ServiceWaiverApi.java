package com.yombu.waiverlibrary;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ServiceWaiverApi {

    @GET(Constants.API_WAIVER_TEMPLATE)
    Call<ModelMyResponse<ModelWaiverTemplate>> getWaiverTemplate(
            @Path("store_id") String storeId,
            @Query("lang") String languageShortCode
    );

    @FormUrlEncoded
    @POST(Constants.API_WAIVER_MINDBODY)
    Call<ModelMyResponse<ModelWaiverProcess>> processMindbodyWaiver(
            @Field("waiver_template_id") String waiverTemplateId,
            @Field("receive_email_notification") String receiveEmailNotification,
            @Field("mindbody_id") String mindbodyId,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("birthday") String birthday,
            @Field("gender") String gender,
            @Field("merchant_lookup") String ciNumber,
            @Field("username") String username,
            @Field("password") String password,
            @Field("signature") String signature,
            @Field("address") String address,
            @Field("city") String city,
            @Field("state") String state,
            @Field("zip") String zip,
            @Field("country") String country,
            @Field("guardian_first_name") String guardianFirstName,
            @Field("guardian_last_name") String guardianLastName,
            @Field("emergency_contact_first_name") String emergencyContactFirstName,
            @Field("emergency_contact_last_name") String emergencyContactLastName,
            @Field("emergency_contact_phone") String emergencyContactPhone,
            @FieldMap Map<String, String> minors,
            @Field("hear_about_us") String hearAboutUsOptions
    );

}