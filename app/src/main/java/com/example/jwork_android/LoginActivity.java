package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Class for Login Activity
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout etEmailLayout, etPasswordLayout;
    private EditText etEmail, etPassword;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // a digit must occur at least once
                    "(?=.*[a-z])" +         // a lower case letter must occur at least once\n" +
                    "(?=.*[A-Z])" +         // an upper case letter must occur at least once"
                    "(?=.*[@#$%^&+=])" +    // at least 1 special character
                    "(?=\\S+$)" +           // no white spaces
                    ".{8,}" +               // at least 4 characters
                    "$");
    private boolean isPressed = false;

    SessionManager sessionManager;

    /**
     * Method when back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (isPressed) {
            finishAffinity();
            System.exit(0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please click back again to exit",
                    Toast.LENGTH_SHORT).show();
            isPressed = true;
        }

        Runnable runnable = new Runnable() {

            /**
             * Method to run the Runnable Thread
             */
            @Override
            public void run() {
                isPressed = false;
            }
        };

        new Handler().postDelayed(runnable, 1000);

    }

    /**
     * Method when Login Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int jobseekerId = extras.getInt("jobseekerId");
        }

        etEmail = findViewById(R.id.editTextTextEmailAddressLogin);
        etPassword = findViewById(R.id.editTextTextPasswordLogin);
        etEmailLayout = findViewById(R.id.editTextEmailLayoutLogin);
        etPasswordLayout = findViewById(R.id.editTextPasswordLayoutLogin);
        Button btnLogin = findViewById(R.id.buttonLogin);
        TextView tvRegister = findViewById(R.id.textViewRegisterNow);

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

        sessionManager = new SessionManager(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button login is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                else {
                    new Thread(new Runnable() {

                        /**
                         * Method to run the Runnable Thread
                         */
                        @Override
                        public void run() {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {

                                /**
                                 * Method when access response
                                 * @param response Response
                                 */
                                @Override
                                public void onResponse(String response) {
                                    System.out.println("On Response Create");
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject != null) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            sessionManager.setLogin(true);
                                            sessionManager.setId(jsonObject.getInt("id"));

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("jobseekerId", jsonObject.getInt("id"));
                                            intent.replaceExtras(intent);
                                            System.out.println("Extras Added Create");
                                            System.out.println("Login ID Extras Create : " + intent.getExtras().getInt("jobseekeerId"));
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                    }
                                    catch (JSONException e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                        builder.setTitle("Incorrect Email or Password");

                                        builder.setMessage("Do you want to try again or register new account?");

                                        builder.setPositiveButton("Register", (dialog, which) -> {
                                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        });

                                        builder.setNegativeButton("TRY AGAIN", (dialog, which) -> dialog.dismiss());

                                        builder.show();
                                    }
                                }
                            };

                            LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                            queue.add(loginRequest);
                        }
                    }).start();
                }
            }
        });

        if(sessionManager.getLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("jobseekerId", sessionManager.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        }

        tvRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when text view register is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
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
     * Method to validate the Password input
     * @return password validation
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