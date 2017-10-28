package com.example.shashankreddy.ecomerecapplicationassignment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shashankreddy.ecomerecapplicationassignment.app.AppController;
import com.example.shashankreddy.ecomerecapplicationassignment.model.CartItems;
import com.example.shashankreddy.ecomerecapplicationassignment.model.Products;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class PaymentFragment extends Fragment {
    private TextView totalItems,totalPrice,responseMessage,responseMessage1;
    private  View shippingView,responseLayout,orderPlaceLayoutInclude,returnView;
    Boolean paypalShiping = false;

    TextInputLayout streetLayout,aptorSuiteLayout,cityLayout,zipcodeLayout;
    TextInputEditText shipmentstreet,shipmentApt,shipmentCity,shipmentZipcode;
    AppCompatSpinner shipmentState,shipmentCountry;
    CardView backToCart,finalConformation;
    String shipmentAddress;
    String country;
    String shipingState;
    String orderId;
    String TAG = PaymentFragment.class.getSimpleName();

    private static PayPalConfiguration mPayPalConfiguration ;
    private PayPalPayment itemsOrdered;
    private static final int REQUEST_CODE_PAYMENT= 1;
    private static final String CONFIG_CLIENT_ID ="ARSqnEbX-D6yfXbX1sBDI62qTxSiizck3kWFBns0gLkyiVT07Wncho_ZmLxmfqJf0QtewSWZWoKR_7-Q";
    public PaymentFragment() {
        mPayPalConfiguration = new PayPalConfiguration().
                environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
                .clientId(CONFIG_CLIENT_ID)
                .merchantName("OnlineBazzar");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, mPayPalConfiguration);
        getActivity().startService(intent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_payment, container, false);
        totalItems = (TextView) returnView.findViewById(R.id.payment_total_items);
        totalPrice = (TextView) returnView.findViewById(R.id.payment_total_price);
        responseLayout = returnView.findViewById(R.id.responseMessage);
        responseMessage =(TextView) returnView.findViewById(R.id.responseText);
        responseMessage1 = (TextView) returnView.findViewById(R.id.responseText1);
        backToCart = (CardView) returnView.findViewById(R.id.editCart);
        finalConformation = (CardView) returnView.findViewById(R.id.finalCheckOut);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Payment");
        }
        shippingView = returnView.findViewById(R.id.shippingLayout);
        orderPlaceLayoutInclude = returnView.findViewById(R.id.orderPlaceLayoutInclude);

        RadioGroup shipingDetails= (RadioGroup) returnView.findViewById(R.id.shipingDetails);
        final RadioButton paypalShippingAddress = (RadioButton) returnView.findViewById(R.id.payPalshippingAddress);
        final RadioButton differentShippingAddress = (RadioButton) returnView.findViewById(R.id.differentshippingAddress);
        shippingLayoutDeatils(shippingView);
        shipingDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == paypalShippingAddress.getId()) {
                    shippingView.setVisibility(View.GONE);
                    paypalShiping = true;
                } else if (checkedId == differentShippingAddress.getId()) {
                    shippingView.setVisibility(View.VISIBLE);
                    paypalShiping = false;
                }
            }
        });
        getTotalValueOfCart();
        backToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentReplaceable, new AddToCart()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });
        finalConformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOrdered = orderStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
                if(paypalShiping)
                    itemsOrdered.enablePayPalShippingAddressesRetrieval(true);
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, mPayPalConfiguration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, itemsOrdered);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);

            }
        });
        return returnView;
    }

    private void shippingLayoutDeatils(final View shippingView) {

        Button confirmAddress;
        streetLayout = (TextInputLayout) shippingView.findViewById(R.id.streetAddressLayout);
        aptorSuiteLayout=(TextInputLayout) shippingView.findViewById(R.id.aptNoLayout);;
        cityLayout=(TextInputLayout) shippingView.findViewById(R.id.cityLayout);
        zipcodeLayout = (TextInputLayout) shippingView.findViewById(R.id.zipCodeLayout);
        shipmentstreet = (TextInputEditText) shippingView.findViewById(R.id.streetAddress);
        shipmentApt = (TextInputEditText) shippingView.findViewById(R.id.aptorSuit);
        shipmentCity= (TextInputEditText) shippingView.findViewById(R.id.shippingCity);
        shipmentZipcode = (TextInputEditText) shippingView.findViewById(R.id.shippingZipcode);
        shipmentState= (AppCompatSpinner) shippingView.findViewById(R.id.shippingState);
        shipmentCountry =(AppCompatSpinner) shippingView.findViewById(R.id.shippingCountry);
        confirmAddress = (Button) shippingView.findViewById(R.id.shippingAddressConfirm);

        ArrayAdapter<CharSequence> stateAdapter =ArrayAdapter.createFromResource(getContext(), R.array.states, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> countryAdapter =ArrayAdapter.createFromResource(getContext(), R.array.countries, android.R.layout.simple_spinner_dropdown_item);

        shipmentState.setAdapter(stateAdapter);
        shipmentCountry.setAdapter(countryAdapter);
        shipmentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shipmentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shipingState = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirmAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!error()){
                    String apt="";
                    if(shipmentApt.getText()!= null)
                        apt = shipmentApt.getText().toString();
                    shipmentAddress = "Address:"+shipmentstreet.getText().toString()+"\t"
                            +apt+"\n City:"+shipmentCity.getText().toString()+", St:"+shipingState
                            +"\n country:"+country+" zipcode:"+shipmentZipcode.getText().toString();
                    shippingView.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean error() {
        if(shipmentstreet.getText()==null){
            streetLayout.setError("please enter Street address");
            return true;
        }else if(shipmentCity.getText()==null){
            aptorSuiteLayout.setError("please enter City");
            return true;
        }else if(shipmentZipcode.getText()==null){
            zipcodeLayout.setError("please enter zipcode");
            return true;
        } else {
            try {
                Integer.parseInt(shipmentZipcode.getText().toString());
            }
            catch (Exception e) {
                zipcodeLayout.setError("please enter valid zipcode");
                return true;
            }
        }
        return false;
    }

    private PayPalPayment orderStuffToBuy(String paymentIntentSale) {
        ArrayList<Products> cartItemList = AppController.getInstance().getCartItems().getCartItemsList();
        HashMap<Products, Integer> quantityMap = AppController.getInstance().getCartItems().getItemQuantity();
        PayPalItem[] items = new PayPalItem[cartItemList.size()];
        for(int i =0; i<cartItemList.size();i++) {
            int quantity = 0;
            double price = (Double.parseDouble(cartItemList.get(i).getPrize()))/67.5;
            if(quantityMap.containsKey(cartItemList.get(i)))
                quantity = quantityMap.get(cartItemList.get(i));
            items[i] = new PayPalItem(cartItemList.get(i).getProductName(),quantity,new BigDecimal(price),"USD",cartItemList.get(i).getProductId());
        }

        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("10.00");

        PayPalPaymentDetails payPalPaymentDetails = new PayPalPaymentDetails(subtotal,shipping,new BigDecimal("0.00"));
        BigDecimal amount = subtotal.add(shipping);
        PayPalPayment payment = new PayPalPayment(amount,"USD","OnlineBazzar",paymentIntentSale);
        payment.items(items).paymentDetails(payPalPaymentDetails);
        payment.custom("please contact 000 on fraud,have a good day");
        return payment;
    }

    private void getTotalValueOfCart() {
        double count = 0;
        int items = 0;
        ArrayList<Products> cartItems = AppController.getInstance().getCartItems().getCartItemsList();
        for (Products selectedItem:cartItems){
            int quantity = AppController.getInstance().getCartItems().getItemQuantity().get(selectedItem);
            items += quantity;
            count = count+quantity*Double.parseDouble(selectedItem.getPrize());
        }
        totalItems.setText("Items :" + items);
        totalPrice.setText("Total : Rs" + count);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PAYMENT){

            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    confirmOrderWith(confirmation);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.d(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    private void confirmOrderWith(final PaymentConfirmation confirmation) {
        ArrayList<Products> cartItemList = AppController.getInstance().getCartItems().getCartItemsList();
        HashMap<Products, Integer> quantityMap = AppController.getInstance().getCartItems().getItemQuantity();
        String itemId ="";
        String itemNames="";
        final String item_id;
        final String item_name;
        int quantity = 0;
        double count = 0;

        ArrayList<Products> cartItems = AppController.getInstance().getCartItems().getCartItemsList();
        for (Products selectedItem:cartItems){
            itemId +=selectedItem.getProductId()+",";
            itemNames += selectedItem.getProductName().replaceAll("\\s+","_")+",";
            quantity += AppController.getInstance().getCartItems().getItemQuantity().get(selectedItem);
            count = count+quantity*Double.parseDouble(selectedItem.getPrize());
        }

        item_id = itemId;
        item_name = itemNames;
        final String items = quantity+"";
        final String total = count+"";

        String orderIdURL= "http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id="
                    +item_id+"&item_names="+ item_name+"&item_quantity="+quantity+"&final_price="+total
                +"&mobile="+AppController.getInstance().getmUserInfo().getMobile();
        JsonObjectRequest orderPlaced = new JsonObjectRequest(Request.Method.GET, orderIdURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray orderIds = response.getJSONArray("Order Confirmed");
                    for(int i = 0;i<orderIds.length();i++){

                        orderId = orderIds.getJSONObject(0).getString("OrderId");
                        setConformation(confirmation);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Log.d(PaymentFragment.class.getSimpleName(),orderPlaced.getUrl());
        AppController.getInstance().addRequestQueue(orderPlaced);
    }

    private void setConformation(PaymentConfirmation confirmation) {

        try {

            JSONObject conformationObject = confirmation.toJSONObject().getJSONObject("response");
            JSONObject paymentConformation = confirmation.getPayment().toJSONObject();
            Log.d(TAG,conformationObject.toString(4)+" "+paymentConformation.toString(4));
            String state= conformationObject.getString("state");
            String time = conformationObject.getString("create_time");
            double amount = Double.parseDouble(paymentConformation.getString("amount"));
            amount = Math.round(amount);
           if(state.equals("approved")){
                orderPlaceLayoutInclude.setVisibility(View.VISIBLE);
                backToCart.setVisibility(View.GONE);
                finalConformation.setVisibility(View.GONE);
                returnView.findViewById(R.id.shipingDetailsLayout).setVisibility(View.GONE);

                TextView conformationText = (TextView) orderPlaceLayoutInclude.findViewById(R.id.payPalConformationText);
                TextView conformationId = (TextView) orderPlaceLayoutInclude.findViewById(R.id.payPalConformationOrderId);
                TextView conformationAmount = (TextView) orderPlaceLayoutInclude.findViewById(R.id.payPalConformationAmount);
                TextView conshippmentAddress = (TextView) orderPlaceLayoutInclude.findViewById(R.id.payPalConformationShipment);
                conformationText.setText("Order is "+state+" and ready for shipment");
                conformationId.setText("Order id : "+orderId);
                conformationAmount.setText("amount: $"+amount);
                conshippmentAddress.setText(shipmentAddress);
           }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
