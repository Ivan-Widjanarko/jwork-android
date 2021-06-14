package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for JobFetchRequest
 *
 * @author Ivan Widjanarko
 * @version 11-06-2021
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";
    private Map<String,String> params;

    /**
     * Constructor for Job Fetch Request
     * @param jobseekerId Jobseeker's ID
     * @param listener Listener
     */
    public JobFetchRequest(String jobseekerId, Response.Listener<String> listener) {
        super(Method.GET, URL+jobseekerId, listener, null);
        params = new HashMap<>();
    }

    /**
     * method for get params
     * @throws AuthFailureError
     * @return params
     */
    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params;
    }
}