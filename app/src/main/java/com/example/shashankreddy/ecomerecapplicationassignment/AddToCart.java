package com.example.shashankreddy.ecomerecapplicationassignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shashankreddy.ecomerecapplicationassignment.adapters.CartAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.example.shashankreddy.ecomerecapplicationassignment.utils.SwipeHelper;

import java.util.ArrayList;


public class AddToCart extends Fragment implements CartAdapter.NotifyFragment, SwipeHelper.OnSwipeDataChange{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private int mParam2;
    private TextView cartTotal,countText;
    private CardView confirmOrder;
    private RelativeLayout emptyCartView;

    public AddToCart() {

    }

    public static AddToCart newInstance(String param1, int param2)  {
        AddToCart fragment = new AddToCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView= inflater.inflate(R.layout.fragment_add_to_cart, container, false);
        RecyclerView cartReyclerView = (RecyclerView) returnView.findViewById(R.id.cartRecyclerView);
        cartTotal = (TextView) returnView.findViewById(R.id.cartTotal);
        confirmOrder = (CardView) returnView.findViewById(R.id.checkoutCart);
        emptyCartView= (RelativeLayout) returnView.findViewById(R.id.emptyCartDistplay);
         if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Cart");
        }

        if(AppController.getInstance().getCartItems().getCartItemsList().size()>0){
            emptyCartView.setVisibility(View.GONE);
            cartReyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CartAdapter adapter = new CartAdapter(getActivity(),this);
            cartReyclerView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new SwipeHelper(0,ItemTouchHelper.LEFT,adapter,this);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(cartReyclerView);
            cartTotal.setText(getTotalValueOfCart());
            confirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentReplaceable,new PaymentFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                }
            });
        }else{
            emptyCartView.setVisibility(View.VISIBLE);
        }

        return returnView;
    }

    public String getTotalValueOfCart() {
        double count = 0;
        ArrayList<Products> cartItems = AppController.getInstance().getCartItems().getCartItemsList();
        for (Products selectedItem:cartItems){
            int quantity = AppController.getInstance().getCartItems().getItemQuantity().get(selectedItem);
            count = count+quantity*Double.parseDouble(selectedItem.getPrize());
        }
        return "$"+count;
    }

    @Override
    public void onItemTotalChanged() {
        cartTotal.setText(getTotalValueOfCart());
        int count=getItemCount();
        if( count>0){
            countText.setVisibility(View.VISIBLE);
            countText.setText(count+"");
        }
    }

    public int getItemCount() {
        int quantity=0;
        ArrayList<Products> cartItems = AppController.getInstance().getCartItems().getCartItemsList();
        for (Products selectedItem:cartItems){
            quantity += AppController.getInstance().getCartItems().getItemQuantity().get(selectedItem);
        }
        return quantity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        View count = menu.findItem(R.id.cart_icon).getActionView();
        countText = (TextView) count.findViewById(R.id.cartViewCountNotify);
    }

    @Override
    public void onSwipeCountChange() {
        cartTotal.setText(getTotalValueOfCart());
        int count = getItemCount();
        if( count>0){
            countText.setVisibility(View.VISIBLE);
            countText.setText(count+"");
        }else if(AppController.getInstance().getCartItems().getCartItemsList().size() == 0)
            emptyCartView.setVisibility(View.VISIBLE);
    }
}
