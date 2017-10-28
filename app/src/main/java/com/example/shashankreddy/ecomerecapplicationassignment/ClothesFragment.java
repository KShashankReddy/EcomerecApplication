package com.example.shashankreddy.ecomerecapplicationassignment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.example.shashankreddy.ecomerecapplicationassignment.model.SubCatageroy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class ClothesFragment extends Fragment {
        private static final String ARG_PARAM1 = "param1";
      private int mParam;
    private ArrayList<CategoryInfo> categoryInfoArrayList;
    private ImageView electronicsImageView;
    private TextView electronicsTittle, electronicsDesc;
    private CardView subFragmentClothesCarView;
    private ArrayList<SubCatageroy> subCatageroyList;
    private ProgressDialog pd;

    public ClothesFragment() {

    }

    public static ClothesFragment newInstance(int param1) {
        ClothesFragment fragment = new ClothesFragment();
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
        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);
        getSubCatategroies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_clothes, container, false);
        electronicsImageView = (ImageView) returnView.findViewById(R.id.subCatageroyClothesImage);
        electronicsTittle = (TextView) returnView.findViewById(R.id.subCatageryClothesTitle);
        electronicsDesc = (TextView) returnView.findViewById(R.id.subCatageryClothesDesc);
        subFragmentClothesCarView = (CardView) returnView.findViewById(R.id.subFragmentClothesCarView);
        subFragmentClothesCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ElectronicProducts electronicProducts = ElectronicProducts.newInstance(Integer.parseInt(subCatageroyList.get(0).getSubCataId()));
                Log.d("ProductFragment", "ClothesProductIsCalled" + subCatageroyList.get(0).getSubCataId());
                fragmentTransaction.replace(R.id.fragmentReplaceable, electronicProducts).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
        return returnView;
    }

    private void getSubCatategroies() {
        pd.show();
        AppController.getInstance().getmSubCatageroy()
                .putSubCatgeroyList(categoryInfoArrayList.get(mParam).getId(), getSubCatategroiesIndi());
    }

    private ArrayList<SubCatageroy> getSubCatategroiesIndi() {
        String subCatageroyUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+
                categoryInfoArrayList.get(mParam).getId();

        subCatageroyList = new ArrayList<>();

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
                        getProducts(subCatageroyId);
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
                if (pd.isShowing())
                    pd.dismiss();
            }
        });
        Log.d("ProductFragment", "ClothesProductIsCalled"+jsonObjectRequest2.getUrl());
        AppController.getInstance().addRequestQueue(jsonObjectRequest2);
        return subCatageroyList;
    }





    private void getProducts(String subCatageroyId) {
        Log.d("ProductFragment", "ClothesProductIsCalled" + subCatageroyId);
        AppController.getInstance().getmProducts()
                .putProductList(subCatageroyId + "", getProductsList(subCatageroyId));
    }
    private ArrayList<Products> getProductsList(final String subCatageroyId) {
        String productURL = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_product.php?Id=206";
        final ArrayList<Products> productList= new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, productURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray productArr = response.optJSONArray("Product");
                Log.d("ProductFragment", "ClothesProductIsCalled" + subCatageroyId);
                for (int j = 0; j < productArr.length(); j++) {
                    JSONObject product = productArr.optJSONObject(j);
                    try {
                        String productId = product.getString("Id");
                        String productName = product.getString("ProductName");
                        String productDesc = product.getString("Discription");
                        String procuctImage = product.getString("Image");
                        String prize = product.getString("Prize");
                        String quantity =product.getString("Quantity");
                        Products products = new Products(productId,productName,procuctImage,quantity,prize,productDesc);
                        productList.add(products);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (pd.isShowing())
                    pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd.isShowing())
                    pd.dismiss();
            }
        });
        Log.d("ProductFragment", "ClothesProductIsCalled"+jsonObjectRequest.getUrl());
        AppController.getInstance().addRequestQueue(jsonObjectRequest);
        return productList;
    }

}
