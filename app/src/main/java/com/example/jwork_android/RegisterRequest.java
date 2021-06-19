package com.example.jwork_android;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Class for Register Request
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class RegisterRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/jobseeker/register";
    private Map<String, String> params;

    /**
     * Constructor for Register Request
     * @param name Jobseeker's Name
     * @param email Jobseeker's Email
     * @param password Jobseeker's Password
     * @param listener Response's Listener
     */
    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
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
