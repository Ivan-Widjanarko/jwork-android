package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for Bonus Request
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class BonusRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/bonus/";
    private Map<String, String> params;

    /**
     * Constructor for BonusRequest
     * @param referralCode Referral Code
     * @param listener Response's Listener
     */
    public BonusRequest(String referralCode, Response.Listener<String> listener){
        super(Method.GET, URL+referralCode, listener, null);
        params = new HashMap<>();
    }

    /**
     * method for get params
     * @throws AuthFailureError Authentication Failed Error
     * @return params
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
