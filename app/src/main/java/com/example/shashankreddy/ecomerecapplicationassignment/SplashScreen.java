package com.example.shashankreddy.ecomerecapplicationassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.UserInfo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("OnlineBazzarLoginStatus",MODE_PRIVATE);
        splashAnimations();

        AdView mAdview = (AdView) findViewById(R.id.adView);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdview.loadAd(mAdRequest);

        progressBar = (ProgressBar) findViewById(R.id.splashProgress);
        progressBar.setMax(5000);
        progressBar.setProgress(0);
        Handler myHanndler = new Handler();
        myHanndler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getString("keepLogin","").equalsIgnoreCase("yes"))
                    logincheckOnSpalsh();
                else
                {
                    Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);

    }


    private void splashAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        /*animation.reset();
        RelativeLayout re = (RelativeLayout) findViewById(R.id.splashScreen);
        re.clearAnimation();
        re.startAnimation(animation);*/

        animation = AnimationUtils.loadAnimation(this,R.anim.translate);
        animation.reset();
        TextView tv = (TextView) findViewById(R.id.appName);
        tv.clearAnimation();
        tv.startAnimation(animation);
    }



    private void logincheckOnSpalsh(){
        String loginUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(SplashScreen.class.getSimpleName(),response.toString());
                if(response.contains("success")){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject userinfo = jsonArray.getJSONObject(i);
                            UserInfo user = AppController.getInstance().getmUserInfo();
                            user.setUserName(userinfo.getString("UserName"));
                            user.setEmail(userinfo.getString("UserEmail"));
                            user.setMobile(userinfo.getString("UserMobile"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d(SplashScreen.class.getSimpleName(), response + "failed");
                    Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(SplashScreen.class.getSimpleName(),error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("mobile",sharedPreferences.getString("userName",""));
                params.put("password",sharedPreferences.getString("password",""));
                Log.d(SplashScreen.class.getSimpleName(), params.toString());
                return params;
            }
        };

        Log.d(SplashScreen.class.getSimpleName(), stringRequest.getUrl());
        AppController.getInstance().addRequestQueue(stringRequest);

    }

}
