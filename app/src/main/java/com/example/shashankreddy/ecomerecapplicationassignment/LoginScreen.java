package com.example.shashankreddy.ecomerecapplicationassignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.UserInfo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
    EditText userName,password;
   TextView signup;
    CheckBox keepLogin;
    Button login;
    SharedPreferences sharedPreferences;
    SignInButton mGoogleSignIn;
    LoginButton mFacebookLogin;
    ProgressDialog progressDialog;
    TextInputLayout user,passwordLayout;
    CallbackManager callbackManager;
    public static final String SPREFS = "OnlineBazzarLoginStatus";
    public static  final String AUTHURL = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login_screen);

        sharedPreferences = getSharedPreferences(SPREFS, Context.MODE_PRIVATE);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        keepLogin = (CheckBox) findViewById(R.id.keepLogin);
        user = (TextInputLayout) findViewById(R.id.usernameLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
//        failureMessage = (TextView) findViewById(R.id.failureMessage);
//        errorMessage = (TextView) findViewById(R.id.errorMessage);

        mFacebookLogin = (LoginButton) findViewById(R.id.facebookLogin);
        mGoogleSignIn = (SignInButton) findViewById(R.id.googleSignIn);
        signup = (TextView) findViewById(R.id.singUp);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);

        user.setErrorEnabled(true);
        passwordLayout.setErrorEnabled(true);
            mGoogleSignIn.setColorScheme(SignInButton.COLOR_DARK);
            mGoogleSignIn.setSize(SignInButton.SIZE_WIDE);

           // mGoogleSignIn.setStyle(SignInButton.COLOR_AUTO,SignInButton.SIZE_STANDARD);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginScreen.this,RegistrationActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            login = (Button) findViewById(R.id.loginButton);
            login.setOnClickListener(new View.OnClickListener() {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                @Override
                public void onClick(View v) {

                    String usernam= userName.getText().toString();
                    String pass = password.getText().toString();
                    if(userName.getText() == null) {
                        user.setError("please Enter Username");
                        return;
                    }
                    if(password.getText() == null) {
                        passwordLayout.setError("please Enter password");
                        return;
                    }
                    if (keepLogin.isChecked()) {
                        editor.putString("keepLogin", "yes");
                        editor.putString("userName", usernam);
                        editor.putString("password", pass);
                        editor.putString("loginBy","registration");
                        editor.commit();
                    } else {
                        editor.putString("keepLogin", "no");
                    }

                    logincheckOnSpalsh();
                }
            });

        facebookLogin();
        googleLogin();
    }

    private void googleLogin() {

    }

    private void facebookLogin() {
        mFacebookLogin.setReadPermissions(Arrays.asList("public_profile, email"));
        callbackManager=CallbackManager.Factory.create();
        mFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();
                Log.d(LoginScreen.class.getSimpleName(), "hello check " + loginResult.getAccessToken());
                GraphRequest graphRequest =  GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d(LoginScreen.class.getSimpleName(), object.toString()+" "+ response.toString());
                        try {
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=small";
                            Log.d(LoginScreen.class.getSimpleName(), name + " " + email + " " + id);
                            UserInfo userInfo = AppController.getInstance().getmUserInfo();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loginBy","faceBook");
                            userInfo.setEmail(email);
                            userInfo.setUserName(name);
                            userInfo.setImage(imageUrl);
                            editor.apply();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            Log.d(LoginScreen.class.getSimpleName(), e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void logincheckOnSpalsh(){
        pdShow();
        String loginUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_login.php?mobile="
                +userName.getText().toString()+"&password="+password.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LoginScreen.class.getSimpleName(), response.toString());
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
                    Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if(response.contains("failure")){
                    //failureMessage.setVisibility(View.VISIBLE);
                    user.setError("please inter valid UserName");
                }else {
                    //errorMessage.setVisibility(View.VISIBLE);
                }
                pdDismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(SplashScreen.class.getSimpleName(), error.getMessage());
                pdDismiss();
            }
        });
        Log.d(LoginScreen.class.getSimpleName(),stringRequest.getUrl());
        AppController.getInstance().addRequestQueue(stringRequest);

    }

    private void pdShow(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void pdDismiss(){
        if(progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}









/*class LoginAsync extends AsyncTask<String, Void, String>{
    URL url = null;
    HttpURLConnection conn;
    ProgressDialog progressDialog;
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog();
        progressDialog.setMessage("please Wait.....");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            url = new URL(AUTHURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("userName",params[0])
                    .appendQueryParameter("email","")
                    .appendQueryParameter("password",params[1]);

            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(query);
            writer.flush();
            writer.close();
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "exception";
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }

        try {
            int response_code = conn.getResponseCode();
            if(response_code == HttpURLConnection.HTTP_OK){
                InputStream is = conn.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine())!=null)
                    sb.append(line);
                return sb.toString();
            }
            else
                return "unSuccessFull";
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(LoginScreen.this, "result is "+s, Toast.LENGTH_SHORT).show();
        if(progressDialog.isShowing())
            progressDialog.cancel();
        try {
//                JSONArray response = new JSONArray(s);
//                JSONObject jsonObject = (JSONObject) response.get(0);
//
//                if(jsonObject.getString("msg").equals("success"))
            if(s.contains("success"))
            {
                Intent i = new Intent(LoginScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else if(s.contains("failure")){
                failureMessage.setVisibility(View.VISIBLE);
            }else if(s.equals("exception")||s.equals("unSuccessFull")){
                errorMessage.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }
}*/
