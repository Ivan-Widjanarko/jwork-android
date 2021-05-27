package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for LoginActivity
 *
 * @author Ivan Widjanarko
 * @version 27-05-2021
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * method for create Login Page
     * @param savedInstanceState saveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = findViewById(R.id.editTextTextEmailAddress);
        final EditText etPassword = findViewById(R.id.editTextTextPassword);
        final Button btnLogin = findViewById(R.id.buttonLogin);
        final TextView tvRegister = findViewById(R.id.textViewRegisterNow);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            /**
             * method for Login (on click)
             * @param view view
             */
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    /**
                     * method for Login (on response)
                     * @param response response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(LoginActivity.this,
                                                    "Login Successful",
                                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,
                                                "Login Failed",
                                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            /**
             * method for Register Now (on click)
             * @param view view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}