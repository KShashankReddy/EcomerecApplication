package com.example.shashankreddy.ecomerecapplicationassignment.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.shashankreddy.ecomerecapplicationassignment.adapters.CartAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {
    CartAdapter mCartAdapter;
    OnSwipeDataChange onSwipeDataChange;
    public SwipeHelper(int dragDirs, int swipeDirs, CartAdapter adapter,OnSwipeDataChange onSwipeDataChange) {
        super(dragDirs, swipeDirs);
        mCartAdapter =adapter;
        this.onSwipeDataChange= onSwipeDataChange;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Products products = AppController.getInstance().getCartItems().getCartItemsList().get(viewHolder.getAdapterPosition());
        AppController.getInstance().getCartItems().getItemQuantity().remove(products);
        AppController.getInstance().getCartItems().getCartItemsList().remove(viewHolder.getAdapterPosition());
        mCartAdapter.notifyDataSetChanged();
        onSwipeDataChange.onSwipeCountChange();
    }

    public interface OnSwipeDataChange{
        void onSwipeCountChange();
    }
}
