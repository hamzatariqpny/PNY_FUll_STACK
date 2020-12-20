package com.pny.pny67_68.ui.activity.retrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pny.pny67_68.repository.model.CategoryResponse;
import com.pny.pny67_68.repository.network.RetrofitBase;

import retrofit2.Call;

public class RetroViewModel extends ViewModel {

    public Call<CategoryResponse> getCategoryData() {
        return RetrofitBase.getRetrofitInstance().getallcatergories();
    }

}
