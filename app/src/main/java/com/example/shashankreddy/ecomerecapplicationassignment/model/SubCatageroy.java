package com.example.shashankreddy.ecomerecapplicationassignment.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SubCatageroy {
    String subCataId,subCatageroyName,subCatageroyDesc,subCatageroyImage;

    public void setSubCatageroyList(HashMap<String, ArrayList<SubCatageroy>> subCatageroyList) {
        this.subCatageroyList = subCatageroyList;
    }

    HashMap<String,ArrayList<SubCatageroy>> subCatageroyList;

    public SubCatageroy() {
        subCatageroyList = new HashMap<>();
    }

    public String getSubCataId() {
        return subCataId;
    }

    public void setSubCataId(String subCataId) {
        this.subCataId = subCataId;
    }

    public String getSubCatageroyName() {
        return subCatageroyName;
    }

    public void setSubCatageroyName(String subCatageroyName) {
        this.subCatageroyName = subCatageroyName;
    }

    public String getSubCatageroyDesc() {
        return subCatageroyDesc;
    }

    public void setSubCatageroyDesc(String subCatageroyDesc) {
        this.subCatageroyDesc = subCatageroyDesc;
    }

    public String getSubCatageroyImage() {
        return subCatageroyImage;
    }

    public void setSubCatageroyImage(String subCatageroyImage) {
        this.subCatageroyImage = subCatageroyImage;
    }

    public HashMap<String, ArrayList<SubCatageroy>> getSubCatageroyList() {
        return subCatageroyList;
    }

    public void putSubCatgeroyList(String catageoryId,ArrayList<SubCatageroy> subCatageroy){

        subCatageroyList.put(catageoryId,subCatageroy);

    }

}
