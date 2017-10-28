package com.example.shashankreddy.ecomerecapplicationassignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CategoryInfo;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.example.shashankreddy.ecomerecapplicationassignment.model.TopSeller;
import com.example.shashankreddy.ecomerecapplicationassignment.model.UserInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView navImageView;
    TextView navUserName,navEmail;
    ArrayList<TopSeller> topSellerList;
    Boolean products;
    Boolean topSellers;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Online Bazzar");
        pd =  new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        navImageView = (ImageView) navHeader.findViewById(R.id.navimageView);
        navUserName = (TextView) navHeader.findViewById(R.id.nav_userName);
        navEmail = (TextView) navHeader.findViewById(R.id.userMailId);
        navHeaderSet();
        getProductsList();
    }


    private void navHeaderSet() {
        UserInfo userInfo = AppController.getInstance().getmUserInfo();
        navUserName.setText(userInfo.getUserName());
        navEmail.setText(userInfo.getEmail());
        if(getSharedPreferences("OnlineBazzarLoginStatus",MODE_PRIVATE).getString("loginBy","").equals("faceBook"))
        Picasso.with(this).load(userInfo.getImage()).into(navImageView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_departments) {
            fragment = new DepartmentsFragment();
        } else if (id == R.id.nav_top_sellers) {
            fragment = new TopSellerFragment();
        } else if (id == R.id.nav_favItems) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_orderStatus) {
            fragment = new TrackOrder();
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this,LogOutActivity.class));
        }else if(id== R.id.nav_settings){
            fragment = new ResetPassword();
        }
        if(fragment != null)
        fragmentManager.beginTransaction().replace(R.id.fragmentReplaceable, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        View count = menu.findItem(R.id.cart_icon).getActionView();
        TextView countText = (TextView) count.findViewById(R.id.cartViewCountNotify);
        int quantity = getItemCount();
        if(quantity>0){
            countText.setVisibility(View.VISIBLE);
            countText.setText(quantity+"");
        }else
        countText.setVisibility(View.GONE);

        final MenuItem item = menu.findItem(R.id.cart_icon);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentReplaceable, new AddToCart()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.cart_icon){
            return  true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void getProductsList() {
        final String productUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php";
        pd.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, productUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray categories = response.optJSONArray("Category");
                ArrayList<CategoryInfo> categoriesList = new ArrayList<>();
                for (int i = 0;i<categories.length();i++){
                    JSONObject category = categories.optJSONObject(i);
                    try {
                        String id = category.getString("Id");
                        String categoryName = category.getString("CatagoryName");
                        String categoryDesc = category.getString("CatagoryDiscription");
                        String image = category.getString("CatagoryImage");
                        CategoryInfo categoryInfo = new CategoryInfo();
                        categoryInfo.setCategoryimage(image);
                        categoryInfo.setCategoryName(categoryName);
                        categoryInfo.setId(id);
                        categoryInfo.setCategoryDesc(categoryDesc);
                        categoriesList.add(categoryInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AppController.getInstance().getmUserInfo().setCategoryInfoList(categoriesList);
                getTopSellerList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
            }
        });

        AppController.getInstance().addRequestQueue(jsonObjectRequest);
    }


    void getTopSellerList(){
        Log.d(TopSellerFragment.class.getSimpleName(), "requestReady");
        final String topSellerUrl ="http://rjtmobile.com/ansari/shopingcart/androidapp/shop_top_sellers.php";
        topSellerList =  new ArrayList<>();
        JsonObjectRequest jonJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, topSellerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray topSellerArray = response.optJSONArray("Top Sellers");

                for(int i=0;i<topSellerArray.length();i++){
                    try {
                        JSONObject topSellerObject =  topSellerArray.getJSONObject(i);
                        String topSellerId = topSellerObject.getString("SellerId");
                        String topSellerName = topSellerObject.getString("SellerName");
                        String topSellerDesc = topSellerObject.getString("SellerDeal");
                        String topSellerRating =topSellerObject.getString("SellerRating");
                        String topSellerImage = topSellerObject.getString("SellerLogo");
                        TopSeller topSeller =  new TopSeller(topSellerId,topSellerName,topSellerDesc,topSellerImage,topSellerRating);
                        Log.d(TopSellerFragment.class.getSimpleName(), topSellerObject.toString());
                        topSellerList.add(topSeller);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppController.getInstance().getmTopSellers().setmTopSeller(topSellerList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragmentReplaceable, new HomeFragment()).commit();
                        }
                    });
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd.isShowing())
                    pd.dismiss();
            }
        });
        AppController.getInstance().addRequestQueue(jonJsonObjectRequest);
    }

    public int getItemCount() {
        int quantity=0;
        ArrayList<Products> cartItems = AppController.getInstance().getCartItems().getCartItemsList();
        for (Products selectedItem:cartItems){
            quantity += AppController.getInstance().getCartItems().getItemQuantity().get(selectedItem);
        }
        return quantity;
    }

}
