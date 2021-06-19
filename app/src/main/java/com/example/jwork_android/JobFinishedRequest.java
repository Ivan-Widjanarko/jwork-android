package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for Job Finished Request
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class JobFinishedRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String invoiceStatus = "Finished";

    /**
     * Constructor for Job Finished Request
     * @param invoiceId Invoice's ID
     * @param listener Response's Listener
     */
    public JobFinishedRequest(String invoiceId, Response.Listener<String> listener) {
        super(Method.PUT, URL+invoiceId, listener, null);
        params = new HashMap<>();
        params.put("invoiceStatus", invoiceStatus);
    }

    /**
     * method for get params
     * @throws AuthFailureError Authentication Failed Error
     * @return params
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}