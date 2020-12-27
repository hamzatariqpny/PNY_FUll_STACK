package com.pny.pny67_68.repository.model;

import java.io.Serializable;

public class User implements Serializable  {

    public String userId;
    public String userName;
    public String userPhone;
    public String userGender;

    public User(String userId, String userName, String userPhone, String userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userGender = userGender;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
