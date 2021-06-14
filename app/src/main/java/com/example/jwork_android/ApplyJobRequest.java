package com.example.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyJobRequest extends StringRequest {

    private static final String URL_Ewallet = "http://10.0.2.2:8080/invoice/createEWalletPayment";
    private static final String URL_Bank = "http://10.0.2.2:8080/invoice/createBankPayment";
    private Map<String, String> params;

    /**
     * Constructor for Login Request
     * @param listener Listener
     */
    public ApplyJobRequest(String jobList, String jobseekerId, String referralCode, Response.Listener<String> listener) {
        super(Method.POST, URL_Ewallet, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobList);
        params.put("jobseekerId", jobseekerId);
        params.put("referralCode", referralCode);
    }

    /**
     * Constructor for Login Request
     * @param listener Listener
     */
    public ApplyJobRequest(String jobList, String jobseekerId, Response.Listener<String> listener) {
        super(Method.POST, URL_Bank, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobList);
        params.put("jobseekerId", jobseekerId);
        params.put("adminFee", "5000");
    }

    /**
     * method for get params
     * @throws AuthFailureError
     * @return params
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
