package com.example.shashankreddy.ecomerecapplicationassignment.model;

import java.util.ArrayList;

/**
 * Created by shashank reddy on 1/4/2017.
 */
public class TopSeller {
    String sellerId,sellerName,selleDeals,sellerImage,sellerRating;
    ArrayList<TopSeller> mTopSeller = new ArrayList<>();;


    public TopSeller(String sellerId, String seellerName, String selleDeals, String sellerImage, String sellerRating) {
        this.sellerId = sellerId;
        this.sellerName = seellerName;
        this.selleDeals = selleDeals;
        this.sellerImage = sellerImage;
        this.sellerRating = sellerRating;
    }

    public TopSeller() {

    }

    public String getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSelleDeals() {
        return selleDeals;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public String getSellerRating() {
        return sellerRating;
    }

    public ArrayList<TopSeller> getmTopSeller() {
        return mTopSeller;
    }

    public void setmTopSeller(ArrayList<TopSeller> mTopSeller) {
        this.mTopSeller = mTopSeller;
    }
}
