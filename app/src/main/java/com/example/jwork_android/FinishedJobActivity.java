package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for Finished Job Activity
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class FinishedJobActivity extends AppCompatActivity {
    private static int jobseekerId;
    private int invoiceId, totalFee, jobFee;
    private String date, paymentType, jobseekerName, jobName, invoiceStatus, referralCode;
    private JSONObject bonus;
    private TextView title, staticJobseekerName, staticInvoiceDate, staticPayment,
            staticInvoiceStatus, staticRefCode, staticJobNameFinished, staticTotalFee,
            jobseeker_name, invoice_date, payment_type, invoice_status, referral_code,
            job_name_invoice, fee_invoice, total_fee_invoice;
    private Button btnCancel, btnFinish;

    /**
     * Method when Finished Job Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_job);

        title = findViewById(R.id.title);
        staticJobseekerName = findViewById(R.id.staticJobseekerName);
        staticInvoiceDate= findViewById(R.id.staticInvoiceDate);
        staticPayment = findViewById(R.id.staticPaymentType);
        staticInvoiceStatus = findViewById(R.id.staticInvoiceStatus);
        staticRefCode = findViewById(R.id.staticReferralCode);
        staticJobNameFinished = findViewById(R.id.staticJobNameTitle);
        staticTotalFee = findViewById(R.id.staticTotalFee);

        jobseeker_name = findViewById(R.id.jobseeker_name);
        invoice_date= findViewById(R.id.invoice_date);
        payment_type = findViewById(R.id.payment_type);
        invoice_status = findViewById(R.id.invoice_status);
        referral_code = findViewById(R.id.referral_code);
        job_name_invoice = findViewById(R.id.job_name_invoice);
        fee_invoice = findViewById(R.id.fee_invoice);
        total_fee_invoice = findViewById(R.id.total_fee_invoice);

        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            jobseekerId = extras.getInt("jobseekerId");
        }

        title.setVisibility(View.INVISIBLE);
        staticJobseekerName.setVisibility(View.INVISIBLE);
        staticInvoiceDate.setVisibility(View.INVISIBLE);
        staticPayment.setVisibility(View.INVISIBLE);
        staticInvoiceStatus.setVisibility(View.INVISIBLE);
        staticRefCode.setVisibility(View.INVISIBLE);
        staticRefCode.setVisibility(View.INVISIBLE);
        staticJobNameFinished.setVisibility(View.INVISIBLE);
        staticTotalFee.setVisibility(View.INVISIBLE);

        jobseeker_name.setVisibility(View.INVISIBLE);
        invoice_date.setVisibility(View.INVISIBLE);
        payment_type.setVisibility(View.INVISIBLE);
        invoice_status.setVisibility(View.INVISIBLE);
        referral_code.setVisibility(View.INVISIBLE);
        job_name_invoice.setVisibility(View.INVISIBLE);
        fee_invoice.setVisibility(View.INVISIBLE);
        total_fee_invoice.setVisibility(View.INVISIBLE);

        btnCancel.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);

        fetchJob();
        clickedButtons();

    }

    /**
     * Method for job fetching
     */
    private void fetchJob(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /**
             * Method when access response
             * @param response Response
             */
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse.length() <= 0) {
                        Toast.makeText(FinishedJobActivity.this, "No Job Applied",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FinishedJobActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    else {
                        title.setVisibility(View.VISIBLE);
                        staticJobseekerName.setVisibility(View.VISIBLE);
                        staticInvoiceDate.setVisibility(View.VISIBLE);
                        staticPayment.setVisibility(View.VISIBLE);
                        staticInvoiceStatus.setVisibility(View.VISIBLE);
                        staticRefCode.setVisibility(View.VISIBLE);
                        staticRefCode.setVisibility(View.VISIBLE);
                        staticJobNameFinished.setVisibility(View.VISIBLE);
                        staticTotalFee.setVisibility(View.VISIBLE);

                        jobseeker_name.setVisibility(View.VISIBLE);
                        invoice_date.setVisibility(View.VISIBLE);
                        payment_type.setVisibility(View.VISIBLE);
                        invoice_status.setVisibility(View.VISIBLE);
                        referral_code.setVisibility(View.VISIBLE);
                        job_name_invoice.setVisibility(View.VISIBLE);
                        fee_invoice.setVisibility(View.VISIBLE);
                        total_fee_invoice.setVisibility(View.VISIBLE);

                        btnCancel.setVisibility(View.VISIBLE);
                        btnFinish.setVisibility(View.VISIBLE);
                    }

                }

                catch (JSONException e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ":" + e.getMessage());
                }

                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0; i<jsonResponse.length(); i++) {
                        JSONObject jsonInvoice = jsonResponse.getJSONObject(i);
                        invoiceStatus = jsonInvoice.getString("invoiceStatus");
                        invoiceId = jsonInvoice.getInt("id");
                        date = jsonInvoice.getString("date");
                        paymentType = jsonInvoice.getString("paymentType");
                        totalFee = jsonInvoice.getInt ("totalFee");
                        referralCode = "No Referral Code";
                        try{
                            bonus = jsonInvoice.getJSONObject("bonus");
                            referralCode = bonus.getString("referralCode");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                        title.setText(getResources().getString(R.string.invoice_id, invoiceId));
                        invoice_date.setText(date.substring(0,10));
                        payment_type.setText(paymentType);
                        total_fee_invoice.setText(String.valueOf(totalFee));
                        invoice_status.setText(invoiceStatus);
                        referral_code.setText(referralCode);

                        JSONObject jsonCustomer = jsonInvoice.getJSONObject("jobseeker");
                        jobseekerName = jsonCustomer.getString("name");
                        jobseeker_name.setText(jobseekerName);

                        if (!invoice_status.getText().equals("OnGoing")) {
                            btnCancel.setEnabled(false);
                            btnFinish.setEnabled(false);
                        }

                        else {
                            btnCancel.setEnabled(true);
                            btnFinish.setEnabled(true);
                        }
                        JSONArray jsonJobs = jsonInvoice.getJSONArray("jobs");
                        for (int j=0; j<jsonJobs.length(); j++) {
                            JSONObject jsonJobObj = jsonJobs.getJSONObject(j);
                            jobName = jsonJobObj.getString("name");
                            job_name_invoice.setText(jobName);

                            jobFee = jsonJobObj.getInt("fee");
                            fee_invoice.setText(String.valueOf(jobFee));
                        }
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JobFetchRequest jobFetchRequest = new JobFetchRequest(String.valueOf(jobseekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
        queue.add(jobFetchRequest);
    }

    /**
     * Method for job clicked button
     */
    private void clickedButtons(){
        btnCancel.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button cancel is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> cancelListener = new Response.Listener<String>() {

                    /**
                     * Method when access response
                     * @param response Response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            finish();
                        }
                        catch (JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Cancel Job Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Toast.makeText(FinishedJobActivity.this, "Job Has Been Canceled",
                        Toast.LENGTH_SHORT).show();
                JobCanceledRequest jobCanceledRequest = new JobCanceledRequest(String.valueOf(invoiceId), cancelListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(jobCanceledRequest);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button finish is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> doneListener = new Response.Listener<String>() {

                    /**
                     * Method when access response
                     * @param response Response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Intent intent = new Intent(FinishedJobActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Toast.makeText(FinishedJobActivity.this, "Job Has Been Finished",
                        Toast.LENGTH_SHORT).show();
                JobFinishedRequest jobFinishedRequest = new JobFinishedRequest(String.valueOf(invoiceId), doneListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(jobFinishedRequest);
            }
        });
    }
}