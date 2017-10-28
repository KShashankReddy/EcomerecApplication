package com.example.shashankreddy.ecomerecapplicationassignment.adapters;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.R;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.TopSeller;
import com.example.shashankreddy.ecomerecapplicationassignment.utils.AnimationUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopSellerAdapter extends RecyclerView.Adapter<TopSellerAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TopSeller> dataSet ;
    TopSellerAdapter mTopSellerAdapter;
    int prev =0;
    public TopSellerAdapter(Context context) {
        this.mContext = context;
        this.dataSet = AppController.getInstance().getmTopSellers().getmTopSeller();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_seller_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(TopSellerAdapter.MyViewHolder holder, int position) {
        TextView  topSellerTitle = holder.topSellerTitle;
        TextView topSellerDesc = holder.topSellerDesc;
        RatingBar  topSellerRating =holder.topSellerRating;
        ImageView  topSellerImageView = holder.topSellerImageView;

        topSellerTitle.setText(dataSet.get(position).getSellerName());
        topSellerDesc.setText(dataSet.get(position).getSelleDeals());
        topSellerRating.setRating(Float.parseFloat(dataSet.get(position).getSellerRating()));
        Picasso.with(mContext).load(dataSet.get(position).getSellerImage()).into(topSellerImageView);

        if(position>position){
            AnimationUtils.animate(holder,true);
        }else{
            AnimationUtils.animate(holder,false);
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addData(ArrayList<TopSeller> topSellerList) {
        this.dataSet = topSellerList;
       notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView topSellerTitle,topSellerDesc;
        RatingBar topSellerRating;
        ImageView topSellerImageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            topSellerTitle = (TextView) itemView.findViewById(R.id.top_seller_title);
            topSellerDesc = (TextView) itemView.findViewById(R.id.top_seller_desc);
            topSellerRating = (RatingBar) itemView.findViewById(R.id.top_seller_rating);
            topSellerImageView = (ImageView) itemView.findViewById(R.id.top_seller_imageView);
        }
    }

}
