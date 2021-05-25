package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for RegisterActivity
 *
 * @author Ivan Widjanarko
 * @version 26-05-2021
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * method for create Register Page
     * @param savedInstanceState saveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = findViewById(R.id.editTextTextPersonNameRegister);
        final EditText etEmail = findViewById(R.id.editTextTextEmailAddressRegister);
        final EditText etPassword = findViewById(R.id.editTextTextPasswordRegister);
        final Button btnRegister = findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * method for Register (on click)
             * @param view view
             */
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    /**
                     * method for Regsiter (on response)
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(RegisterActivity.this,
                                                    "Register Successful",
                                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this,
                                                "Register Failed",
                                                    Toast.LENGTH_LONG).show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}