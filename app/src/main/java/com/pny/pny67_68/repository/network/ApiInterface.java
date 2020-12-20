package com.pny.pny67_68.repository.network;

import androidx.lifecycle.LiveData;

import com.pny.pny67_68.repository.model.CategoryResponse;
import com.pny.pny67_68.repository.model.SendBodyData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("afs-user-service/v1/speakup-category")
    Call<CategoryResponse> getallcatergories();

    @POST("sendDataToServer")
    Call<String> sendData(@Body SendBodyData sendBodyData);

}
