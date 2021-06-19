package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Class for Menu Request
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class MainRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/job";
    private Map<String, String> params;

    /**
     * Constructor for MenuRequest
     * @param listener Response's Listener
     */
    public MainRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
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
