package com.example.shashankreddy.ecomerecapplicationassignment.app;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CartItems;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CategoryInfo;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Order;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.example.shashankreddy.ecomerecapplicationassignment.model.SubCatageroy;
import com.example.shashankreddy.ecomerecapplicationassignment.model.TopSeller;
import com.example.shashankreddy.ecomerecapplicationassignment.model.UserInfo;


public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
   private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private static UserInfo mUserInfo;
    private static CategoryInfo mcategoryInfo;
    private static SubCatageroy mSubCatageroy;
    private static Products mProducts;
    private static TopSeller mTopSellers;
    private static CartItems cartItems;
    private static Order mOrder;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    public  RequestQueue getRequestQueue(){

        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return this.mRequestQueue;
    }

    public <T> void  addRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void callPendingRequest(Object tag){
        if(mRequestQueue != null)
            getRequestQueue().cancelAll(tag);
    }


    public UserInfo getmUserInfo(){
        if(mUserInfo == null) {
            Log.d(AppController.class.getSimpleName(),"userInfoInstanceReturned");
            mUserInfo = new UserInfo();
        }
        return mUserInfo;
    }

    public CategoryInfo getCategoryInfo(){
        if(mcategoryInfo == null)
            mcategoryInfo = new CategoryInfo();

        return mcategoryInfo;
    }

    public SubCatageroy getmSubCatageroy(){
        if(mSubCatageroy == null)
            mSubCatageroy = new SubCatageroy();
        return mSubCatageroy;
    }

    public Products getmProducts(){
        if(mProducts == null)
            mProducts = new Products();
        return mProducts;
    }

    public TopSeller getmTopSellers(){
        if(mTopSellers == null)
            mTopSellers = new TopSeller();
        return mTopSellers;
    }

    public CartItems getCartItems(){
        if(cartItems == null)
            cartItems = new CartItems();
        return cartItems;
    }

    public Order getmOrder(){
        if(mOrder == null)
            mOrder = new Order();
        return mOrder;
    }
}
