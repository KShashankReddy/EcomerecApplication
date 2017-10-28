package com.example.shashankreddy.ecomerecapplicationassignment.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.R;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CategoryInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    ArrayList<CategoryInfo> categoryInfoArrayList;
    Context mContext;
    OnRecyclerViewItemClicked mOnRecyclerViewItemClicked;

    public interface OnRecyclerViewItemClicked{
        void onItemClicked(View itemView,int position);
    }

    public HomeAdapter(Context context,OnRecyclerViewItemClicked onRecyclerViewItemClicked){
        this.mContext = context;
        this.mOnRecyclerViewItemClicked = onRecyclerViewItemClicked;
        categoryInfoArrayList = AppController.getInstance().getmUserInfo().getCategoryInfoList();
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_layout,parent,false);
        HomeHolder homeHolder = new HomeHolder(view);
        return homeHolder;
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        ImageView catageroyImageView =holder.catageroyImageView;
        TextView catageroyTitle= holder.catageroyTitle;
        TextView catageroyDescription = holder.catageroyDescription;

        catageroyTitle.setText(categoryInfoArrayList.get(position).getCategoryName());
        catageroyDescription.setText(categoryInfoArrayList.get(position).getCategoryDesc());
        Picasso.with(mContext).load(categoryInfoArrayList.get(position).getCategoryimage()).into(catageroyImageView);
    }

    @Override
    public int getItemCount() {
        return categoryInfoArrayList.size();
    }

    public class HomeHolder extends RecyclerView.ViewHolder {
        ImageView catageroyImageView;
        TextView catageroyTitle,catageroyDescription;
        public HomeHolder(final View itemView) {
            super(itemView);
            catageroyImageView = (ImageView) itemView.findViewById(R.id.homeCardImageView);
            catageroyTitle = (TextView) itemView.findViewById(R.id.cardViewTitle);
            catageroyDescription= (TextView) itemView.findViewById(R.id.cardViewDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecyclerViewItemClicked.onItemClicked(itemView,getLayoutPosition());
                }
            });
        }
    }
}
