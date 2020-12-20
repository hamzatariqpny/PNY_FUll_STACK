package com.pny.pny67_68.repository.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static final String BASE_URL = "http://192.168.1.23:8081/";

    public static ApiInterface getRetrofitInstance(){

            return new Retrofit.
                    Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface.class);

    }
}
