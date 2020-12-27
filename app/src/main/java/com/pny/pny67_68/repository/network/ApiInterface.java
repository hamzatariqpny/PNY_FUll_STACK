package com.pny.pny67_68.repository.network;

import com.pny.pny67_68.repository.model.CategoryResponse;
import com.pny.pny67_68.repository.model.SendBodyData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("afs-user-service/v1/speakup-category")
    Call<CategoryResponse> getallcatergories();

    @POST("sendDataToServer")
    Call<String> sendData(@Body SendBodyData sendBodyData);

    @GET("https://disease.sh/v3/covid-19/countries/{country}")
    Call<String> sendData(@Path("country") String country);

}
