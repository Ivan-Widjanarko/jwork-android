package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

/**
 * Class for LoginRequest
 *
 * @author Ivan Widjanarko
 * @version 26-05-2021
 */
public class LoginRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/jobseeker/login";
    private Map<String, String> params;

    /**
     * Constructor for Login Request
     * @param email Jobseeker's Email
     * @param password Jobseeker's Password
     * @param listener Listener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * method for get params
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
