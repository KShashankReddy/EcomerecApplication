package com.example.shashankreddy.ecomerecapplicationassignment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPassword extends Fragment {
    String oldPassword,newPassword;
    EditText mOldPasswordEdit,mRenterPasswordEdit,mNewPasswordEdit;
    TextView missMatchError,wrongPasswordError;
    private ProgressDialog pd;

    public ResetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_reset_password, container, false);
        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);
        pd.setMessage("updating");
       mOldPasswordEdit= (EditText) view.findViewById(R.id.oldPasswordEdit);
        mRenterPasswordEdit= (EditText) view.findViewById(R.id.reEnterPasswordEnter);
        mNewPasswordEdit =(EditText) view.findViewById(R.id.newPasswordEdit);
        wrongPasswordError = (TextView) view.findViewById(R.id.passwordWrong);
        missMatchError = (TextView) view.findViewById(R.id.mismatchText);
        Button resetPassword = (Button) view.findViewById(R.id.resetPassword);
        mRenterPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                missMatchError.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mOldPasswordEdit.getText().toString().equals(mRenterPasswordEdit.getText().toString())) {
                    missMatchError.setText("Password matched");
                    oldPassword = mOldPasswordEdit.getText().toString();
                    missMatchError.setTextColor(getResources().getColor(R.color.hint_color, null));
                } else
                    missMatchError.setVisibility(View.VISIBLE);
                missMatchError.setText("Password didn't match oldPassword");
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = mNewPasswordEdit.getText().toString();
                if(!oldPassword.equals("")&& !newPassword.equals("") && oldPassword != null && newPassword != null){
                    resetPasswordCall();
                }else{
                    wrongPasswordError.setText("please Enter Password again");
                }
            }
        });

        return view;
    }

    void resetPasswordCall(){
        pd.show();
        String resetUrl = "http://rjtmobile.com/ansari/shopingcart/androidapp/shop_reset_pass.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, resetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("password reset successfully")){
                    startActivity(new Intent(getActivity(),LoginScreen.class));
                }else if(response.contains("old password mismatch")){
                    wrongPasswordError.setText("entered old Password is wrong");
                }else if(response.contains("wrong mobile number")){

                }
                if(pd.isShowing())
                    pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd.isShowing())
                    pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("mobile", AppController.getInstance().getmUserInfo().getMobile());
                params.put("password",oldPassword);
                params.put("newPassword",newPassword);
                return params;
            }
        };
        Log.d(ResetPassword.class.getSimpleName(),stringRequest.getUrl());
        AppController.getInstance().addRequestQueue(stringRequest);
    }
}
