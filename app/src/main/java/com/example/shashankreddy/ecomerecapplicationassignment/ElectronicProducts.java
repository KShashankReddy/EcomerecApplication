package com.example.shashankreddy.ecomerecapplicationassignment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shashankreddy.ecomerecapplicationassignment.adapters.ElectronicProductAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.SubCatageroy;


import java.util.ArrayList;


public class ElectronicProducts extends Fragment {
    private static final String ARG_PARAM1 = "param";
    ArrayList<SubCatageroy> subCatageroyArrayList;
    private int mParam;
    private  String id;
    private RecyclerView mProductRecyclerView;

    public ElectronicProducts() {
        id=AppController.getInstance().getmUserInfo().getCategoryInfoList().get(mParam).getId();
    }

    public static ElectronicProducts newInstance(int param1) {
        ElectronicProducts fragment = new ElectronicProducts();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView =inflater.inflate(R.layout.fragment_electronic_products, container, false);
        mProductRecyclerView = (RecyclerView) returnView.findViewById(R.id.productsRecyclerView);
        mProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ElectronicProductAdapter adapter = new ElectronicProductAdapter(getActivity(),mParam);
        mProductRecyclerView.setAdapter(adapter);
        return returnView;
    }

}
