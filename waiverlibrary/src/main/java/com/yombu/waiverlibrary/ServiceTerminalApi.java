package com.yombu.waiverlibrary;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface ServiceTerminalApi {

    @FormUrlEncoded
    @POST(Constants.API_TERMINAL_INITIALIZATION)
    Call<ModelDeviceInitialization> initialize(
            @Path("rid") String resellerId,
            @Field("mac") String macId,
            @Field("public_key") String publicKey,
            @Field("serial") String serialNumber,
            @Field("product_id") String productId,
            @Field("mid") String merchantId,
            @Field("api_key") String apiKey,
            @Field("location_id") String locationId
    );

}
