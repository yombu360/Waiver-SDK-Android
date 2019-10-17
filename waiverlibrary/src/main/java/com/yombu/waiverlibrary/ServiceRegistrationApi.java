package com.yombu.waiverlibrary;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceRegistrationApi {

    @FormUrlEncoded
    @POST(Constants.API_WAIVER)
    Call<ModelWaiverProcess> processWaiver(
            @Field("waiver_template_id") String waiverTemplateId,
            @Field("receive_email_notification") String receiveEmailNotification,
            @Field("signature") String signature,
            @FieldMap Map<String, String> userData,
            @FieldMap Map<String, String> minors
    );

}
