package com.example.shashankreddy.ecomerecapplicationassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.HashMap;


public class MobilePhonesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private int mParam;
    private ArrayList<CategoryInfo> categoryInfoArrayList;
    private ImageView mobilePhonesImageView;
    private TextView mobilePhonesTittle, mobilePhonesDesc;

    public MobilePhonesFragment() {

    }

    public static MobilePhonesFragment newInstance(int param1) {
        MobilePhonesFragment fragment = new MobilePhonesFragment();
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
        getSubCatategroies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_mobile_phones, container, false);
        mobilePhonesImageView = (ImageView) returnView.findViewById(R.id.subCatageroyMobileImage);
        mobilePhonesTittle = (TextView) returnView.findViewById(R.id.subCatageryMobileTitle);
        mobilePhonesDesc = (TextView) returnView.findViewById(R.id.subCatageryMobileDesc);
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

                        mobilePhonesTittle.setText(subCatageroyList.get(j).getSubCatageroyName());
                        mobilePhonesDesc.setText(subCatageryDesc);
                        Picasso.with(getActivity()).load(subCatageroyList.get(j).getSubCatageroyImage()).into(mobilePhonesImageView);
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
