package com.pny.pny67_68.ui;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pny.pny67_68.R;
import com.pny.pny67_68.ui.model.CategoriesData;
import com.pny.pny67_68.ui.wm.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonActivity extends AppCompatActivity {

    String url = "http://192.168.1.23:8081/afs-user-service/v1/speakup-category";
    String postUrl = "http://dummy.restapiexample.com/api/v1/create";
    RequestQueue requestQueue;

    ArrayList<CategoriesData> categoriesDataList = new ArrayList<>();
    RecyclerView categoryRv;
    CategoryAdapter categoryAdapter;
    ProgressBar progessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        requestQueue = Volley.newRequestQueue(this);
        getCategoriesData();
        categoryRv = findViewById(R.id.categoryRv);
        progessBar = findViewById(R.id.progessBar);
        categoryRv.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this , categoriesDataList);
        categoryRv.setAdapter(categoryAdapter);

    }


    public void getCategoriesData() {

        // 1) TYPE GET,POST,PUT,DELETE
        // 2) URL
        // 3) Response callback
        // 4) Error callback

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progessBar.setVisibility(View.GONE);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progessBar.setVisibility(View.GONE);
                Toast.makeText(JsonActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);

    }

    public void parseJson(String response) {

        try {
            JSONObject data = new JSONObject(response);

            int status = data.optInt("code", 0);

            if (status == 200) {

                JSONArray daraArr = data.getJSONArray("data");

                for (int i = 0; i < daraArr.length(); i++) {

                    JSONObject category = daraArr.getJSONObject(i);

                    CategoriesData categoriesData = new CategoriesData();

                    categoriesData.setId(category.optInt("id", 0));
                    categoriesData.setCategoryName(category.optString("categoryName", ""));
                    categoriesData.setDescription(category.optString("description", ""));
                    categoriesData.setIcon(category.optString("icon", ""));
                    categoriesData.setUuid(category.optString("uuid", ""));

                    categoriesDataList.add(categoriesData);

                }

                categoryAdapter.notifyDataSetChanged();


            } else {
                Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void postApiData() {
        // edit text
        JSONObject sendingData = new JSONObject();
        try {
            sendingData.put("name", "hamza tariq");
            sendingData.put("salary", "2000");
            sendingData.put("age", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 1) TYPE GET,POST,PUT,DELETE
        // 2) URL
        // 3) Json object data
        // 4) Response callback
        // 5) Error callback
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                sendingData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(JsonActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JsonActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });


        requestQueue.add(jsonObjectRequest);


    }


}