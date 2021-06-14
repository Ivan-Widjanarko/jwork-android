package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerId, jobId, bonus;
    private String jobName, jobCategory, selectedPayment;
    private double jobFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobId = extras.getInt("job_id");
            jobName = extras.getString("job_name");
            jobCategory = extras.getString("job_category");
            jobFee = extras.getInt("job_fee");
            jobseekerId = extras.getInt("jobseekerId");
        }

        Button btnApply = findViewById(R.id.btnApply);
        TextView textCode = findViewById(R.id.textCode);
        EditText referral_code = findViewById(R.id.referral_code);
        TextView job_name = findViewById(R.id.job_name);
        TextView job_category = findViewById(R.id.job_category);
        TextView job_fee = findViewById(R.id.job_fee);
        TextView total_fee = findViewById(R.id.total_fee);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button hitung = findViewById(R.id.hitung);


        btnApply.setVisibility(View.INVISIBLE);
        textCode.setVisibility(View.INVISIBLE);
        referral_code.setVisibility(View.INVISIBLE);

        job_name.setText(jobName);
        job_category.setText(jobCategory);
        job_fee.setText(Double.toString(jobFee));
        total_fee.setText("0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                switch (checkedId) {
                    case R.id.ewallet:
                        textCode.setVisibility(View.VISIBLE);
                        referral_code.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bank:
                        textCode.setVisibility(View.INVISIBLE);
                        referral_code.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                switch (radioId) {
                    case R.id.ewallet:

                        String referralCode = referral_code.getText().toString();
                        final Response.Listener<String> bonusResponse = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (referralCode.isEmpty()) {
                                    Toast.makeText(ApplyJobActivity.this, "Referral Code Not Entered",
                                            Toast.LENGTH_SHORT).show();
                                    total_fee.setText(Double.toString(jobFee));
                                }
                                else {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        int extraFee = jsonResponse.getInt("extraFee");
                                        int minTotalFee = jsonResponse.getInt("minTotalFee");
                                        boolean bonusStatus = jsonResponse.getBoolean("active");

                                        if (!bonusStatus) {
                                            Toast.makeText(ApplyJobActivity.this, "Unavailable Bonus",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else if (bonusStatus) {
                                            if (jobFee < extraFee || jobFee < minTotalFee) {
                                                Toast.makeText(ApplyJobActivity.this, "Invalid Referral Code",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(ApplyJobActivity.this, "Referral Code Applied",
                                                        Toast.LENGTH_SHORT).show();
                                                total_fee.setText(Double.toString(jobFee + extraFee));
                                            }
                                        }
                                    }

                                    catch (JSONException e) {
                                        Toast.makeText(ApplyJobActivity.this, "Referral Code Not Found",
                                                Toast.LENGTH_SHORT).show();
                                        total_fee.setText(Double.toString(jobFee));
                                    }
                                }

                            }
                        };
                        Response.ErrorListener errorReferralCode = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "Error Happened", error);
                            }
                        };

                        BonusRequest bonusRequest = new BonusRequest(referralCode, bonusResponse);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                        break;

                    case R.id.bank:
                        total_fee.setText(Double.toString(jobFee));
                        break;
                }

                hitung.setVisibility(View.INVISIBLE);
                btnApply.setVisibility(View.VISIBLE);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                ApplyJobRequest request = null;

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(ApplyJobActivity.this, "Apply Successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else {
                                Toast.makeText(ApplyJobActivity.this, "Apply Failed",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        catch (JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Apply Failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(selectedRadioId == R.id.bank) {
                    request = new ApplyJobRequest(String.valueOf(jobId), String.valueOf(jobseekerId), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }

                else if(selectedRadioId == R.id.ewallet) {
                    request = new ApplyJobRequest(String.valueOf(jobId), String.valueOf(jobseekerId), referral_code.getText().toString(), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }
            }
        });
    }
}