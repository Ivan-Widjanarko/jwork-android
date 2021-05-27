package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Class for MenuReuest
 *
 * @author Ivan Widjanarko
 * @version 27-05-2021
 */
public class MenuRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/job";
    private Map<String, String> params;

    /**
     * Constructor for Login Request
     */
    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
    }
}
