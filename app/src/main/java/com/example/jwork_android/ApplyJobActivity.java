package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for Apply Job Activity
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class ApplyJobActivity extends AppCompatActivity {

    private int jobseekerId, jobId;
    private String jobName, jobCategory;
    private double jobFee;

    /**
     * Method when Apply Job Page is created
     * @param savedInstanceState Instance's State
     */
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
        Button btnCount = findViewById(R.id.count);

        btnCount.setEnabled(true);
        btnApply.setEnabled(false);
        textCode.setVisibility(View.INVISIBLE);
        referral_code.setVisibility(View.INVISIBLE);

        job_name.setText(jobName);
        job_category.setText(jobCategory);
        job_fee.setText(Double.toString(jobFee));
        total_fee.setText("0");

        referral_code.addTextChangedListener(new TextWatcher() {

            /**
             * Method when referral code input is changed
             * @param s Character Sequences
             * @param start Start
             * @param before Before
             * @param count Count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCount.setEnabled(true);
                btnApply.setEnabled(false);
            }

            /**
             * Method after referral code input is changed
             * @param s Character
             */
            @Override
            public void afterTextChanged(Editable s) {
                btnCount.setEnabled(true);
                btnApply.setEnabled(false);
            }

            /**
             * Method before referral code input is changed
             * @param s Character Sequences
             * @param start Start
             * @param count Count
             * @param after After
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnCount.setEnabled(true);
                btnApply.setEnabled(false);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            /**
             * Method when radio group checked
             * @param radioGroup radio group's button
             * @param checkedId id of the checked radio group
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.eWallet:
                        textCode.setVisibility(View.VISIBLE);
                        referral_code.setVisibility(View.VISIBLE);
                        btnCount.setEnabled(true);
                        btnApply.setEnabled(false);
                        break;
                    case R.id.bank:
                        textCode.setVisibility(View.INVISIBLE);
                        referral_code.setVisibility(View.INVISIBLE);
                        btnCount.setEnabled(true);
                        btnApply.setEnabled(false);
                        break;
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button count is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                switch (radioId) {
                    case R.id.eWallet:

                        String referralCode = referral_code.getText().toString();
                        final Response.Listener<String> bonusResponse = new Response.Listener<String>() {

                            /**
                             * Method when access response
                             * @param response Response
                             */
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

                            /**
                             * Method when response accessing generate error
                             * @param error error
                             */
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
                        total_fee.setText(Double.toString(jobFee - 5000));
                        break;
                }

                btnCount.setEnabled(false);
                btnApply.setEnabled(true);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button apply is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                String referralCode = referral_code.getText().toString();

                ApplyJobRequest request = null;

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    /**
                     * Method when access response
                     * @param response Response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.length() > 0) {
                                if (selectedRadioId == R.id.eWallet) {
                                    if (referralCode.isEmpty()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ApplyJobActivity.this);

                                        builder.setTitle("Empty Referral Code");

                                        builder.setMessage("Are you sure you want to apply without referral code?");

                                        builder.setPositiveButton("YES", (dialog, which) -> {
                                            Toast.makeText(ApplyJobActivity.this, "Apply Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        });

                                        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

                                        builder.show();
                                    }
                                    else {
                                        Toast.makeText(ApplyJobActivity.this, "Apply Successful",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                else if (selectedRadioId == R.id.bank) {
                                    Toast.makeText(ApplyJobActivity.this, "Apply Successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            else {
                                Toast.makeText(ApplyJobActivity.this, "Apply Failed",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }

                        catch (JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Error Happened when Applying",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };

                if(selectedRadioId == R.id.bank) {
                    request = new ApplyJobRequest(String.valueOf(jobId), String.valueOf(jobseekerId), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }

                else if(selectedRadioId == R.id.eWallet) {
                    request = new ApplyJobRequest(String.valueOf(jobId), String.valueOf(jobseekerId), referral_code.getText().toString(), responseListener);
                    RequestQueue q = Volley.newRequestQueue(ApplyJobActivity.this);
                    q.add(request);
                }
            }
        });
    }
}