package com.yombu.waiverlibrary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ServiceWaiverApi {

    @GET(Constants.API_WAIVER_TEMPLATE)
    Call<ModelMyResponse<ModelWaiverTemplate>> getWaiverTemplate(
            @Path("store_id") String storeId,
            @Query("lang") String languageShortCode
    );

}