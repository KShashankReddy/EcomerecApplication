package com.example.shashankreddy.ecomerecapplicationassignment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CategoryInfo;
import com.example.shashankreddy.ecomerecapplicationassignment.model.SubCatageroy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShoesFragment extends Fragment{
    private ImageView electronicsImageView;
    private TextView electronicsTittle, electronicsDesc;
    private ArrayList<CategoryInfo> categoryInfoArrayList;
    private static final String ARG_PARAM1 = "param";
    private int mParam;

    public ShoesFragment() {

    }

    public static ShoesFragment newInstance(int param1) {
        ShoesFragment fragment = new ShoesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM1);
        }
        this.categoryInfoArrayList = AppController.getInstance().getmUserInfo().getCategoryInfoList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_electronics, container, false);
        electronicsImageView = (ImageView) returnView.findViewById(R.id.subFragmentImageView);
        electronicsTittle = (TextView) returnView.findViewById(R.id.subCatageryTitle);
        electronicsDesc = (TextView) returnView.findViewById(R.id.subCatageryDesc);
        getSubCatategroies();
        Log.d("SubCatageory", "SubCatageory is called" + mParam);
        return returnView;
    }


    private void getSubCatategroies() {
        AppController.getInstance().getmSubCatageroy()
                .putSubCatgeroyList(categoryInfoArrayList.get(mParam).getId(), getSubCatategroiesIndi());
    }

    private ArrayList<SubCatageroy> getSubCatategroiesIndi() {
        String subCatageroyUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+
                categoryInfoArrayList.get(mParam).getId();

        final ArrayList<SubCatageroy> subCatageroyList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, subCatageroyUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray subCategoryArr = response.optJSONArray("SubCategory");
                for (int j = 0; j < subCategoryArr.length(); j++) {
                    JSONObject subCat = subCategoryArr.optJSONObject(j);
                    try {
                        String subCatageroyId = subCat.getString("Id");
                        String subCatageroyName = subCat.getString("SubCatagoryName");
                        String subCatageryDesc = subCat.getString("SubCatagoryDiscription");
                        String subCatageroyImage = subCat.getString("CatagoryImage");
                        SubCatageroy subCatageroy = new SubCatageroy();
                        subCatageroy.setSubCataId(subCatageroyId);
                        subCatageroy.setSubCatageroyName(subCatageroyName);
                        subCatageroy.setSubCatageroyDesc(subCatageryDesc);
                        subCatageroy.setSubCatageroyImage(subCatageroyImage);
                        subCatageroyList.add(subCatageroy);
                        electronicsTittle.setText(subCatageroyList.get(j).getSubCatageroyName());
                        electronicsDesc.setText(subCatageryDesc);
                        Picasso.with(getActivity()).load(subCatageroyList.get(j).getSubCatageroyImage()).into(electronicsImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(DepartmentsFragment.class.getSimpleName(), error.getMessage());
            }
        });

        AppController.getInstance().addRequestQueue(jsonObjectRequest2);
        return subCatageroyList;
    }


}
