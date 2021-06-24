package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
 * @version 25-06-2021
 */
public class FinishedJobActivity extends AppCompatActivity {

    private static int jobseekerId;
    private int invoiceId, totalFee, jobFee;
    private String date, paymentType, jobseekerName, jobName, invoiceStatus, referralCode;
    private JSONObject bonus;
    private TextView title;
    private TextView jobseeker_name;
    private TextView invoice_date;
    private TextView payment_type;
    private TextView invoice_status;
    private TextView referral_code;
    private TextView job_name_invoice;
    private TextView fee_invoice;
    private TextView total_fee_invoice;
    private Button btnCancel, btnFinish;
    private LinearLayout layoutFinished;
    private int flag = 0;

    /**
     * Method when Finished Job Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_job);

        title = findViewById(R.id.title);

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

        layoutFinished = findViewById(R.id.layout_finished);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            jobseekerId = extras.getInt("jobseekerId");
        }

        layoutFinished.setVisibility(View.INVISIBLE);

        fetchJob();
        clickedButtons();

    }

    /**
     * Method for job fetching
     */
    @SuppressLint("ClickableViewAccessibility")
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinishedJobActivity.this);

                        builder.setTitle("Empty History");

                        builder.setMessage("Sorry, there are no history yet");

                        builder.setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(FinishedJobActivity.this, MainActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        });

                        builder.show();
                    }

                    else {
                        layoutFinished.setVisibility(View.VISIBLE);
                    }

                }

                catch (JSONException e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ":" + e.getMessage());
                }

                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    layoutFinished.setOnTouchListener(new OnSwipeTouchListener(FinishedJobActivity.this) {

                        /**
                         * Method when swiping the slide from right to the left
                         * @throws JSONException JSON Exception
                         */
                        public void onSwipeRight() throws JSONException {
                            flag +=1;
                            if (flag <= jsonResponse.length()) {
                                JSONObject jsonInvoice = jsonResponse.getJSONObject(flag);
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
                                    btnCancel.setVisibility(View.GONE);
                                    btnFinish.setVisibility(View.GONE);
                                } else {
                                    btnCancel.setVisibility(View.VISIBLE);
                                    btnFinish.setVisibility(View.VISIBLE);
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
                            else {
                                Toast.makeText(FinishedJobActivity.this, "This is The Last Invoice",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        /**
                         * Method when swiping the slide from left to the right
                         * @throws JSONException JSON Exception
                         */
                        public void onSwipeLeft() throws JSONException {
                            flag -=1;
                            if (flag >= 0) {
                                JSONObject jsonInvoice = jsonResponse.getJSONObject(flag);
                                invoiceStatus = jsonInvoice.getString("invoiceStatus");
                                invoiceId = jsonInvoice.getInt("id");
                                date = jsonInvoice.getString("date");
                                paymentType = jsonInvoice.getString("paymentType");
                                totalFee = jsonInvoice.getInt("totalFee");
                                referralCode = "No Referral Code";
                                try {
                                    bonus = jsonInvoice.getJSONObject("bonus");
                                    referralCode = bonus.getString("referralCode");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                title.setText(getResources().getString(R.string.invoice_id, invoiceId));
                                invoice_date.setText(date.substring(0, 10));
                                payment_type.setText(paymentType);
                                total_fee_invoice.setText(String.valueOf(totalFee));
                                invoice_status.setText(invoiceStatus);
                                referral_code.setText(referralCode);

                                JSONObject jsonCustomer = jsonInvoice.getJSONObject("jobseeker");
                                jobseekerName = jsonCustomer.getString("name");
                                jobseeker_name.setText(jobseekerName);

                                if (!invoice_status.getText().equals("OnGoing")) {
                                    btnCancel.setVisibility(View.GONE);
                                    btnFinish.setVisibility(View.GONE);
                                } else {
                                    btnCancel.setVisibility(View.VISIBLE);
                                    btnFinish.setVisibility(View.VISIBLE);
                                }

                                JSONArray jsonJobs = jsonInvoice.getJSONArray("jobs");
                                for (int j = 0; j < jsonJobs.length(); j++) {
                                    JSONObject jsonJobObj = jsonJobs.getJSONObject(j);
                                    jobName = jsonJobObj.getString("name");
                                    job_name_invoice.setText(jobName);

                                    jobFee = jsonJobObj.getInt("fee");
                                    fee_invoice.setText(String.valueOf(jobFee));
                                }
                            }
                            else {
                                    Toast.makeText(FinishedJobActivity.this, "This is The First Invoice",
                                            Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                    JSONObject jsonInvoice = jsonResponse.getJSONObject(flag);
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
                        btnCancel.setVisibility(View.GONE);
                        btnFinish.setVisibility(View.GONE);
                    } else {
                        btnCancel.setVisibility(View.VISIBLE);
                        btnFinish.setVisibility(View.VISIBLE);
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
                            Intent intent = new Intent(FinishedJobActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Cancel Job Failed",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
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
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Finish Job Failed",
                                    Toast.LENGTH_SHORT).show();
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