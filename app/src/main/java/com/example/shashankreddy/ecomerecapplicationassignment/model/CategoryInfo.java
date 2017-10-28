package com.example.shashankreddy.ecomerecapplicationassignment.model;

import java.util.ArrayList;

public class CategoryInfo {

    String id;
    String categoryName;
    String categoryDesc;
    String categoryimage;

    ArrayList<SubCatageroy> subCatageroyList = new ArrayList<>();;

    public ArrayList<CategoryInfo> getCategoryInfoList() {
        return categoryInfoList;
    }

    public void setCategoryInfoList(ArrayList<CategoryInfo> categoryInfoList) {
        this.categoryInfoList = categoryInfoList;
    }

    ArrayList<CategoryInfo> categoryInfoList;
    public void setId(String id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public void setCategoryimage(String categoryimage) {
        this.categoryimage = categoryimage;
    }



    public CategoryInfo() {

    }

    public String getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public String getCategoryimage() {
        return categoryimage;
    }

    public ArrayList<SubCatageroy> getSubCatageroyList() {
        return subCatageroyList;
    }

    public void setSubCatageroyList(ArrayList<SubCatageroy> subCatageroyList) {
        this.subCatageroyList = subCatageroyList;
    }
}
