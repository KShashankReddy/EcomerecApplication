package com.example.shashankreddy.ecomerecapplicationassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.shashankreddy.ecomerecapplicationassignment.adapters.HomeAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.adapters.TopSellerAdapter;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class HomeFragment extends Fragment implements HomeAdapter.OnRecyclerViewItemClicked{

    public static final int PRODUCT_DATA = 1;
    public static final int TOPSELLER_DATA = 2;
    TopSellerAdapter adapter1;
    HomeAdapter adapter;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView =inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView mRecyclerView = (RecyclerView) returnView.findViewById(R.id.home_fragment_recyclerView);
        RecyclerView mRecyclerTopSeller = (RecyclerView) returnView.findViewById(R.id.topSell_fragment_recyclerView);

        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        }

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new HomeAdapter(getActivity(),this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerTopSeller.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter1 = new TopSellerAdapter(getActivity());
        mRecyclerTopSeller.setAdapter(adapter1);
        return returnView;
    }

    @Override
    public void onItemClicked(View itemView, int position) {


    }

    public void notifyFromActivity(int i ){

        if(i == HomeFragment.PRODUCT_DATA){
            adapter.notifyDataSetChanged();
        }else if(i == HomeFragment.TOPSELLER_DATA){
            adapter1.notifyDataSetChanged();
        }
    }
}
