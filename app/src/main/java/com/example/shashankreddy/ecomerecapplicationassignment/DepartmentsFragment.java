package com.example.shashankreddy.ecomerecapplicationassignment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shashankreddy.ecomerecapplicationassignment.adapters.DeparmentsPagerAdapter;




public class DepartmentsFragment extends Fragment {


    private TabLayout departmentsTabLayout;
    private ViewPager departmentViewPager;
    ProgressDialog pd;

    public DepartmentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView =  inflater.inflate(R.layout.fragment_departments, container, false);
        DeparmentsPagerAdapter pagerAdapter = new DeparmentsPagerAdapter(getChildFragmentManager());
        departmentsTabLayout = (TabLayout) returnView.findViewById(R.id.departments_tab_layout);
        departmentViewPager = (ViewPager) returnView.findViewById(R.id.departments_view_pager);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Departments");
        }
        departmentsTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        departmentsTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        departmentsTabLayout.setupWithViewPager(departmentViewPager);
        departmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(departmentsTabLayout));
        departmentViewPager.setAdapter(pagerAdapter);
        return returnView;
    }

}
