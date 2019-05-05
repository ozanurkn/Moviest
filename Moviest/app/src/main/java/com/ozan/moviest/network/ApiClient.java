package com.ozan.moviest.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ozan.moviest.helper.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2,TimeUnit.MINUTES)
                .writeTimeout(2,TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
