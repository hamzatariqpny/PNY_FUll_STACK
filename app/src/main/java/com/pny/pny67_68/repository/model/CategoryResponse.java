package com.pny.pny67_68.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("code")
    int code;

    @SerializedName("status")
    String status;

    @SerializedName("mesisage")
    String message;

    @SerializedName("data")
    List<CategoriesData> categoriesData;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoriesData> getCategoriesData() {
        return categoriesData;
    }

    public void setCategoriesData(List<CategoriesData> categoriesData) {
        this.categoriesData = categoriesData;
    }
}

