package com.example.shashankreddy.ecomerecapplicationassignment.adapters;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.ProductView;
import com.example.shashankreddy.ecomerecapplicationassignment.R;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ElectronicProductAdapter extends RecyclerView.Adapter<ElectronicProductAdapter.ProductHolder> {
    private FragmentActivity mContext;
    String mParam;
    ArrayList<Products>  productsArrayList;
    public ElectronicProductAdapter(Context context, int mParam) {
        this.mContext = (FragmentActivity) context;
        this.mParam = mParam+"";
        productsArrayList = AppController.getInstance().getmProducts().getProductList().get(this.mParam);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        ProductHolder productHolder = new ProductHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, final int position) {
        TextView productName = holder.productName;
        TextView productDesc= holder.productDesc;
        TextView productPrice= holder.productPrice;
        TextView productQuant= holder.productQuant;
        ImageView productImage = holder.productImage;
        CardView electronicProductCard = holder.electronicProductCard;

        productName.setText(productsArrayList.get(position).getProductName());
        productDesc.setText(productsArrayList.get(position).getDescription());
        productPrice.setText(productsArrayList.get(position).getPrize());
        productQuant.setText(productsArrayList.get(position).getQuantity());

        Picasso.with(mContext).load(productsArrayList.get(position).getProductImage()).
                into(productImage);

        electronicProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductView productView = ProductView.newInstance(mParam,position);
                FragmentTransaction fragmentTransaction  = mContext.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentReplaceable,productView).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        TextView productName,productDesc,productPrice,productQuant;
        ImageView productImage;
        CardView electronicProductCard;
        public ProductHolder(View itemView) {
            super(itemView);
            productName= (TextView) itemView.findViewById(R.id.productTitle);
            productDesc= (TextView) itemView.findViewById(R.id.productDesc);
            productPrice= (TextView) itemView.findViewById(R.id.productPrice);
            productQuant= (TextView) itemView.findViewById(R.id.productQuantity);
            productImage = (ImageView) itemView.findViewById(R.id.productImageView);
            electronicProductCard = (CardView) itemView.findViewById(R.id.productCardView);
        }
    }
}
