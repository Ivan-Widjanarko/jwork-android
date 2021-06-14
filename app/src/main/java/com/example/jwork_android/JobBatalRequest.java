package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for JobBatalRequest
 *
 * @author Ivan Widjanarko
 * @version 11-06-2021
 */
public class JobBatalRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String invoiceStatus = "Cancelled";

    /**
     * Constructor for Job Batal Request
     * @param invoiceId Invoice's ID
     * @param listener Listener
     */
    public JobBatalRequest (String invoiceId, Response.Listener<String> listener) {
        super(Method.PUT, URL+invoiceId, listener, null);
        params = new HashMap<>();
        params.put("id", invoiceId);
        params.put("status", invoiceStatus);
    }

    /**
     * method for get params
     * @throws AuthFailureError
     * @return params
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}