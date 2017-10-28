package com.example.shashankreddy.ecomerecapplicationassignment.model;

import java.util.ArrayList;

public class UserInfo {

    String userName;
    String email;
    String mobile;
    String image;
    ArrayList<CategoryInfo> categoryInfoList;
    private String password;

    public ArrayList<CategoryInfo> getCategoryInfoList() {
        return categoryInfoList;
    }

    public void setCategoryInfoList(ArrayList<CategoryInfo> categoryInfoList) {
        this.categoryInfoList = categoryInfoList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserInfo() {

    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getImage() {
        return image;
    }

    public UserInfo(String userName, String email, String mobile, String image) {

        this.userName = userName;
        this.email = email;
        this.mobile = mobile;
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
