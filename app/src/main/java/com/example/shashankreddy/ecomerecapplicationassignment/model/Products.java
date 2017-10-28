package com.example.shashankreddy.ecomerecapplicationassignment.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Products {

    String productId;
    String productName;
    String productImage;
    String quantity;
    String prize;
    String description;
    HashMap<String,ArrayList<Products>> productList = new HashMap<>();

    public Products() {

    }

    public HashMap<String, ArrayList<Products>> getProductList() {
        return productList;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String id) {
        this.productId = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void putProductList(String catageoryId, ArrayList<Products> products){
        productList.put(catageoryId,products);
    }

    public Products(String productId, String productName, String productImage, String quantity, String prize, String description) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.prize = prize;
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == productId ? 0 : productId.hashCode());
        hash = 31 * hash + (null == productName ? 0 : productName.hashCode());
        hash = 31 * hash + (null == prize ? 0 : prize.hashCode());
        hash = 31 * hash + (null == quantity ? 0 : quantity.hashCode());
        hash = 31 * hash + (null == description ? 0 : description.hashCode());
        hash = 31 * hash + (null == productImage ? 0 : productImage.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
        	return false;
        Products product = (Products)obj;
        return  (productId == product.productId || (productId != null && productId.equals(product.productId)));
    }
}
