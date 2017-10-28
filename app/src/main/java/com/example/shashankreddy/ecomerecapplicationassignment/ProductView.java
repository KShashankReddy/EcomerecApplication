package com.example.shashankreddy.ecomerecapplicationassignment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CartItems;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.squareup.picasso.Picasso;

public class ProductView extends DialogFragment {
    private Products selectedProduct;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView productViewTitle,productViewDesc,productViewPrice,productViewQuantity;
    ImageView productViewImage;
    Button addToCartButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;


    public ProductView() {

    }

    public static ProductView newInstance(String param1, int param2) {
        ProductView fragment = new ProductView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            selectedProduct = AppController.getInstance().getmProducts().getProductList().get(mParam1).get(mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_product_view, container, false);
        productViewTitle = (TextView) returnView.findViewById(R.id.productViewTitle);
        productViewDesc = (TextView) returnView.findViewById(R.id.productViewDesc);
        productViewPrice = (TextView) returnView.findViewById(R.id.productViewPrice);
        productViewQuantity = (TextView) returnView.findViewById(R.id.productViewQuantity);
        addToCartButton = (Button) returnView.findViewById(R.id.productAddToCart);
        productViewImage = (ImageView) returnView.findViewById(R.id.productViewImage);


        productViewTitle.setText(selectedProduct.getProductName());
        productViewDesc.setText(selectedProduct.getDescription());
        productViewPrice.setText("Price:"+selectedProduct.getPrize());
        productViewQuantity.setText("Quantity left:" + selectedProduct.getQuantity());
        Picasso.with(getContext()).load(selectedProduct.getProductImage()).into(productViewImage);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(AppController.getInstance().getCartItems().getItemQuantity().containsKey(selectedProduct))) {
                    CartItems cartItems = AppController.getInstance().getCartItems();
                    Log.d("CartCheck","FirstTime entered");
                    cartItems.addToCartList(selectedProduct);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentReplaceable, AddToCart.newInstance(mParam1, mParam2)).commit();
                    fragmentTransaction.addToBackStack(null);
                }
                else{
                    CartItems cartItems = AppController.getInstance().getCartItems();
                    cartItems.putintoHashMap(selectedProduct,1);
                    Log.d("CartCheck", "secondTime entered");
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentReplaceable, AddToCart.newInstance(mParam1, mParam2)).commit();
                    fragmentTransaction.addToBackStack(null);
                }
            }
        });

        return returnView;
    }

}
