package com.example.shashankreddy.ecomerecapplicationassignment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.adapters.TrackAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackOrder extends Fragment {
    TrackAdapter mTrackAdapter;
    ProgressDialog pd;
    ArrayList<Order> orderHistory;

    public TrackOrder() {
        orderHistory = new ArrayList<>();
    }

    private void getOrderHistory() {
        pd.show();
        String orderHisURL =  "http://rjtmobile.com/ansari/shopingcart/androidapp/order_history.php?&mobile="
                +AppController.getInstance().getmUserInfo().getMobile();
        JsonObjectRequest js0nJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderHisURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TrackOrder.class.getSimpleName(), response.toString());
                try {
                    JSONArray history = response.getJSONArray("Order History");
                    for(int i = 0; i <history.length();i++){
                        JSONObject jsonObject = history.getJSONObject(i);
                        String orderId = jsonObject.getString("OrderID");
                        String itemNams = jsonObject.getString("ItemName");
                        String quantity = jsonObject.getString("ItemQuantity");
                        String  finalPrice= jsonObject.getString("FinalPrice");
                        String orderStatus = jsonObject.getString("OrderStatus");
                        Order order = new Order(orderId,quantity,finalPrice,orderStatus,itemNams);
                        Log.d(TrackOrder.class.getSimpleName(),orderId+" "+itemNams+" "+quantity+" "+finalPrice);
                        orderHistory.add(order);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AppController.getInstance().getmOrder().setmOrderList(orderHistory);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTrackAdapter.notifyDataSetChanged();
                        mTrackAdapter.notifyOnDataChange();
                    }
                });


                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params =  new HashMap<>();
                params.put("mobile", AppController.getInstance().getmUserInfo().getMobile());
                return params;
            }
        };
        Log.d(TrackOrder.class.getSimpleName(),js0nJsonObjectRequest.getUrl());
        AppController.getInstance().addRequestQueue(js0nJsonObjectRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_track_order, container, false);
        RecyclerView mRecyclerView = (RecyclerView) returnView.findViewById(R.id.trackOrderRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Order Status");
        }
        mTrackAdapter = new TrackAdapter(getActivity(),orderHistory);
        mRecyclerView.setAdapter(mTrackAdapter);
        pd =  new ProgressDialog(getContext());
        pd.setMessage("trackingHistory");
        getOrderHistory();
        return returnView;
    }

}
