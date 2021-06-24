package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Class for Register Activity
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class RegisterActivity extends AppCompatActivity {
    
    private TextInputLayout etNameLayout, etEmailLayout, etPasswordLayout;
    private EditText etName, etEmail, etPassword;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // a digit must occur at least once
                    "(?=.*[a-z])" +         // a lower case letter must occur at least once\n" +
                    "(?=.*[A-Z])" +         // an upper case letter must occur at least once"
                    "(?=.*[@#$%^&+=])" +    // at least 1 special character
                    "(?=\\S+$)" +           // no white spaces
                    ".{8,}" +               // at least 4 characters
                    "$");

    /**
     * Method when Register Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.editTextTextPersonNameRegister);
        etEmail = findViewById(R.id.editTextTextEmailAddressRegister);
        etPassword = findViewById(R.id.editTextTextPasswordRegister);
        etNameLayout = findViewById(R.id.editTextNameLayoutRegister);
        etEmailLayout = findViewById(R.id.editTextEmailLayoutRegister);
        etPasswordLayout = findViewById(R.id.editTextPasswordLayoutRegister);
        Button btnRegister = findViewById(R.id.buttonRegister);

        etName.addTextChangedListener(new TextWatcher() {

            /**
             * Method when name input is changed
             * @param s Character Sequences
             * @param start Start
             * @param before Before
             * @param count Count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etNameLayout.setError(null);
            }

            /**
             * Method after name input is changed
             * @param s Character
             */
            @Override
            public void afterTextChanged(Editable s) {
                etNameLayout.setError(null);
            }

            /**
             * Method before name input is changed
             * @param s Character Sequences
             * @param start Start
             * @param count Count
             * @param after After
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etNameLayout.setError(null);
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {

            /**
             * Method when email input is changed
             * @param s Character Sequences
             * @param start Start
             * @param before Before
             * @param count Count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etEmailLayout.setError(null);
            }

            /**
             * Method after email input is changed
             * @param s Character
             */
            @Override
            public void afterTextChanged(Editable s) {
                etEmailLayout.setError(null);
            }

            /**
             * Method before email input is changed
             * @param s Character Sequences
             * @param start Start
             * @param count Count
             * @param after After
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etEmailLayout.setError(null);
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {

            /**
             * Method when password input is changed
             * @param s Character Sequences
             * @param start Start
             * @param before Before
             * @param count Count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPasswordLayout.setError(null);
            }

            /**
             * Method after password input is changed
             * @param s Character
             */
            @Override
            public void afterTextChanged(Editable s) {
                etPasswordLayout.setError(null);
            }

            /**
             * Method before password input is changed
             * @param s Character Sequences
             * @param start Start
             * @param count Count
             * @param after After
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etPasswordLayout.setError(null);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button register is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if (!validateEmail() | !validatePassword() | !validateName()) {
                    return;
                }
                else {
                    new Thread(new Runnable() {

                        /**
                         * Method to run the Runnable Thread
                         */
                        @Override
                        public void run() {
                            runOnUiThread(() -> {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    /**
                                     * Method when access response
                                     * @param response Response
                                     */
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject != null) {
                                                Toast.makeText(RegisterActivity.this,
                                                        "Register Successful",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this,
                                                        LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                        catch (JSONException e) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                                            builder.setTitle("Email already exists");

                                            builder.setMessage("Do you want to login with the existing email?");

                                            builder.setPositiveButton("YES", (dialog, which) -> {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finishAffinity();
                                            });

                                            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

                                            builder.show();
                                        }
                                    }
                                };

                                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                queue.add(registerRequest);
                            });
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * Method to validate the name input
     * @return email validation
     */
    private boolean validateName() {
        String nameInput = etName.getText().toString();

        if (nameInput.isEmpty()) {
            etNameLayout.setError("Name can not be empty");
            etNameLayout.requestFocus();
            return false;
        }
        else {
            etNameLayout.setError(null);
            return true;
        }
    }

    /**
     * Method to validate the email input
     * @return email validation
     */
    private boolean validateEmail() {
        String emailInput = etEmail.getText().toString();

        if (emailInput.isEmpty()) {
            etEmailLayout.setError("Email can not be empty");
            etEmailLayout.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmailLayout.setError("Please enter a valid email address");
            etEmailLayout.requestFocus();
            return false;
        }
        else {
            etEmailLayout.setError(null);
            return true;
        }
    }

    /**
     * Method to validate the password input
     * @return email validation
     */
    private boolean validatePassword() {
        String passwordInput = etPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            etPasswordLayout.setError("Password can not be empty");
            etPasswordLayout.setErrorIconDrawable(null);
            etPasswordLayout.requestFocus();
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPasswordLayout.setError("Please enter a valid password format\n" +
                    "Minimum consist of :\n" +
                    "\t\t1 digit\n" +
                    "\t\t1 lower case letter\n" +
                    "\t\t1 upper case letter\n" +
                    "\t\t1 special character\n" +
                    "\t\tNo white spaces\n" +
                    "\t\tAt least 8 characters");
            etPasswordLayout.setErrorIconDrawable(null);
            etPasswordLayout.requestFocus();
            return false;
        }
        else {
            etPasswordLayout.setError(null);
            return true;
        }
    }
}