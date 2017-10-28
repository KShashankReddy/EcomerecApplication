package com.example.shashankreddy.ecomerecapplicationassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;

public class LogOutActivity extends AppCompatActivity {
    SharedPreferences msharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button reLogin = (Button) findViewById(R.id.logoutLogin);
        msharedPreference = getSharedPreferences("OnlineBazzarLoginStatus",MODE_PRIVATE);
        String status = msharedPreference.getString("loginBy","");
        Log.d(LogOutActivity.class.getSimpleName(), status + " has to be changed");
        if(!status.equals("")) {
            SharedPreferences.Editor editor = msharedPreference.edit();
            if (status.equals("registration")) {
                editor.putString("keepLogin", "logOut");
                editor.clear();
                Log.d(LogOutActivity.class.getSimpleName(), status + " has to be changed");
                startActivity(new Intent(LogOutActivity.this, LoginScreen.class));
            } else if (status.equals("faceBook")) {
                editor.putString("keepLogin", "logOut");
                Log.d(LogOutActivity.class.getSimpleName(), LoginManager.getInstance().toString()+" "+status);
                LoginManager.getInstance().logOut();
                editor.clear();
            }
            editor.apply();

        }
        reLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogOutActivity.this, LoginScreen.class));
                }
        });
    }

}
