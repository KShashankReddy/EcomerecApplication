package com.example.shashankreddy.ecomerecapplicationassignment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.adapters.TopSellerAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.TopSeller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Handler;


public class TopSellerFragment extends Fragment {
    RecyclerView mRecyclerView;
    TopSellerAdapter mTopSellerAdapter;
    public TopSellerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView =inflater.inflate(R.layout.fragment_top_seller, container, false);
        mRecyclerView = (RecyclerView) returnView.findViewById(R.id.topSellerRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTopSellerAdapter=new TopSellerAdapter(getActivity());
        mRecyclerView.setAdapter(mTopSellerAdapter);
        return returnView;
    }

}
