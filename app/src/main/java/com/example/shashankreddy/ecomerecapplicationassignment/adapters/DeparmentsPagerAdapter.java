package com.example.shashankreddy.ecomerecapplicationassignment.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.shashankreddy.ecomerecapplicationassignment.ClothesFragment;
import com.example.shashankreddy.ecomerecapplicationassignment.ElectronicsFragment;
import com.example.shashankreddy.ecomerecapplicationassignment.JewelleryFragment;
import com.example.shashankreddy.ecomerecapplicationassignment.MobilePhonesFragment;
import com.example.shashankreddy.ecomerecapplicationassignment.ShoesFragment;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CategoryInfo;

import java.util.ArrayList;


public class DeparmentsPagerAdapter extends FragmentStatePagerAdapter{
    private ArrayList<CategoryInfo> categoryInfoArrayList;
    public DeparmentsPagerAdapter(FragmentManager fm) {
        super(fm);
        categoryInfoArrayList=AppController.getInstance().getmUserInfo().getCategoryInfoList();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ElectronicsFragment electronicsFragment = ElectronicsFragment.newInstance(0);
                return electronicsFragment;
            case 1:
                ClothesFragment clothesFragment = ClothesFragment.newInstance(1);
                return clothesFragment;
            case 2:
                JewelleryFragment jewelleryFragment = JewelleryFragment.newInstance(2);
                return jewelleryFragment;
            case 3:
                ShoesFragment shoesFragment =  ShoesFragment.newInstance(3);
                return shoesFragment;
            case 4:
                MobilePhonesFragment mobilePhones = MobilePhonesFragment.newInstance(4);
                return mobilePhones;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return categoryInfoArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryInfoArrayList.get(position).getCategoryName();
       // return "electronics";
    }

}
