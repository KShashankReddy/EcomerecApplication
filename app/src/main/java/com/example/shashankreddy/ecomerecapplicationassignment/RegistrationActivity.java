package com.example.shashankreddy.ecomerecapplicationassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText fullName,email,mobileNumber,password;
    CheckBox passwordShow;
    Button registratinButton,loginButton;
    TextInputLayout fullNameLayout,emailLayout,mobileLayout,passwordLayout;
    public static final String SPREFS = "OnlineBazzarLoginStatus";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fullName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        password = (EditText) findViewById(R.id.password);
        passwordShow = (CheckBox) findViewById(R.id.passwordShow);
        registratinButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.revertLoginScreen);
        fullNameLayout = (TextInputLayout) findViewById(R.id.fullNameLayout);
        emailLayout=(TextInputLayout) findViewById(R.id.emailLayout);
        mobileLayout = (TextInputLayout) findViewById(R.id.mobileLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);

        passwordShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                else
                    password.setInputType(129);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        registratinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateValues())
                postRegesterData();
            }
        });
    }

    private boolean validateValues() {
        if(fullName.getText().toString() == null){
           fullNameLayout.setError("Enter Valid RegistrationName");
            return false;
        }
        if(mobileNumber.getText().toString() == null){
            mobileLayout.setError("Enter Valid MobileNumber");
            return false;
        }
        if(password.getText().toString() == null){
            passwordLayout.setError("EnterPassword");
            return false;
        }

        return true;
    }

    private void postRegesterData() {
        final String registrationURL = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reg.php?%20name="+
                fullName.getText().toString()+
                "&email="+email.getText().toString()+"&mobile="+mobileNumber.getText().toString()+"&password="
                +password.getText().toString()+"\n";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registrationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("successfully registered")){
                    Log.d(RegistrationActivity.class.getSimpleName(), response);
                    SharedPreferences sharedPreferences = getSharedPreferences(SPREFS, Context.MODE_PRIVATE);
                    UserInfo userInfo = AppController.getInstance().getmUserInfo();
                    userInfo.setUserName(fullName.getText().toString());
                    userInfo.setEmail(email.getText().toString());
                    userInfo.setMobile(mobileNumber.getText().toString());
                    userInfo.setPassword(password.getText().toString());

                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                }
                else {
                    //snackBar
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(RegistrationActivity.class.getSimpleName(),error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String ,String> params = new HashMap<>();
                params.put("name",fullName.getText().toString());
                params.put("email",email.getText().toString());
                params.put("mobile",mobileNumber.getText().toString());
                params.put("password",password.getText().toString());

                return super.getParams();
            }
        };
        AppController.getInstance().addRequestQueue(stringRequest);
    }

}
