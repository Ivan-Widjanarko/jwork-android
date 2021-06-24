package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for Jobseeker Fetch Request
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class JobseekerFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/";
    private final Map<String,String> params;

    /**
     * Constructor for Jobseeker Fetch Request
     * @param jobseekerId Jobseeker's ID
     * @param listener Response's Listener
     */
    public JobseekerFetchRequest(String jobseekerId, Response.Listener<String> listener) {
        super(Method.GET, URL+jobseekerId, listener, null);
        params = new HashMap<>();
    }

    /**
     * method for get params
     * @throws AuthFailureError Authentication Failed Error
     * @return params
     */
    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params;
    }
}