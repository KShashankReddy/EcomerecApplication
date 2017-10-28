package com.example.shashankreddy.ecomerecapplicationassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Order implements Parcelable {
    String orderId;
    String items;
    String total;
    String orderStatus;

    public Order() {

    }

    protected Order(Parcel in) {
        orderId = in.readString();
        items = in.readString();
        total = in.readString();
        orderStatus = in.readString();
        itemNames = in.readString();
        mOrderList = in.createTypedArrayList(Order.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getItemNames() {
        return itemNames;
    }

    String itemNames;
    ArrayList<Order> mOrderList = new ArrayList<Order>();

    public Order(String orderId, String items, String total, String orderStatus, String itemNames) {
        this.orderId = orderId;
        this.items = items;
        this.total = total;
        this.orderStatus = orderStatus;
        this.itemNames = itemNames;
    }

    public void setmOrderList(ArrayList<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }

    public String getOrderId() {

        return orderId;
    }

    public String getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public ArrayList<Order> getmOrderList() {
        return mOrderList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(items);
        dest.writeString(total);
        dest.writeString(orderStatus);
        dest.writeString(itemNames);
        dest.writeTypedList(mOrderList);
    }
}
