package com.example.shashankreddy.ecomerecapplicationassignment.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class CartItems {
    private ArrayList<Products> cartItemsList;
    private HashMap<Products,Integer> itemQuantity;

    public HashMap<Products, Integer> getItemQuantity() {
        return itemQuantity;
    }

    public CartItems() {
        cartItemsList = new ArrayList<Products>();
        itemQuantity = new HashMap<>();
    }


    public ArrayList<Products> getCartItemsList() {
        return cartItemsList;

    }

    public void addToCartList(Products selectedProduct) {
        cartItemsList.add(selectedProduct);
        itemQuantity.put(selectedProduct, 1);
    }

    public void putintoHashMap(Products selectedProduct,int i){
        int quantity = itemQuantity.get(selectedProduct);
        itemQuantity.put(selectedProduct,quantity+i);
    }

    public void clearCart(){
        cartItemsList.clear();
        itemQuantity.clear();
    }
}
