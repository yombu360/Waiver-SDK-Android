package com.yombu.waiverlibrary;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    public static Retrofit getNewClient(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();
                request = requestBuilder
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("charset", "utf-8")
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = builder.connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getNewClientWithToken(String baseUrl, final Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // TODO: Remove this interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();
                request = requestBuilder
                        .addHeader("Access-Token", Terminal.getInstance(context.getApplicationContext()).getAccessToken())
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("charset", "utf-8")
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = builder.connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
