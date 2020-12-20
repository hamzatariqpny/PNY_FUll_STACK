package com.pny.pny67_68.ui.activity.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    RetroViewModel retroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);

        retroViewModel = viewModelProvider.get(RetroViewModel.class);


        Call<CategoryResponse> call = retroViewModel.getCategoryData();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                CategoryResponse categoryResponse = response.body();

                Toast.makeText(RetrofitActivity.this, categoryResponse.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}