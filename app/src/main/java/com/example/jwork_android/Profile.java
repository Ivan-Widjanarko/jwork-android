package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class for Profile Page
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu, btProfile;
    RecyclerView recyclerView;

    private static int jobseekerId;
    private String name, email, password, joinDate;
    private TextView jobseeker_id;
    private TextView jobseeker_name;
    private TextView jobseeker_email;
    private TextView jobseeker_password;
    private TextView jobseeker_join_date;
    private Button btnRemove, btnLogout;

    SessionManager sessionManager;

    /**
     * Method when Profile Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);
        btProfile = findViewById(R.id.bt_profile);

        jobseeker_id = findViewById(R.id.jobseeker_id);
        jobseeker_name = findViewById(R.id.jobseeker_name);
        jobseeker_email = findViewById(R.id.jobseeker_email);
        jobseeker_password = findViewById(R.id.jobseeker_password);
        jobseeker_join_date = findViewById(R.id.jobseeker_join_date);

        btnRemove = findViewById(R.id.btnRemove);
        btnLogout = findViewById(R.id.btnLogout);

        sessionManager = new SessionManager(getApplicationContext());

        int sId = sessionManager.getId();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(this, MainActivity.arrayList));

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            jobseekerId = extras.getInt("jobseekerId");
        }

        fetchJobseeker();
        clickedButtons();

        btMenu.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button menu is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button profile is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Profile.class);
                intent.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
            }
        });
    }

    /**
     * Method for jobseeker fetching
     */
    private void fetchJobseeker(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /**
             * Method when access response
             * @param response Response
             */
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonJobseeker = new JSONObject(response);
                        jobseekerId = jsonJobseeker.getInt("id");
                        name = jsonJobseeker.getString("name");
                        email = jsonJobseeker.getString("email");
                        password = jsonJobseeker.getString("password");
                        joinDate = jsonJobseeker.getString("joinDate");

                        jobseeker_id.setText(getResources().getString(R.string.jobseeker_id, jobseekerId));
                        jobseeker_name.setText(name);
                        jobseeker_email.setText(email);
                        jobseeker_password.setText(password);
                        jobseeker_join_date.setText(joinDate.substring(0,10));

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JobseekerFetchRequest jobseekerFetchRequest = new JobseekerFetchRequest(String.valueOf(jobseekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        queue.add(jobseekerFetchRequest);
    }

    /**
     * Method for job clicked button
     */
    private void clickedButtons(){
        btnRemove.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button remove is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Response.Listener<String> removeListener = new Response.Listener<String>() {

                    /**
                     * Method when access response
                     * @param response Response
                     */
                    @Override
                    public void onResponse(String response) {
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);

                builder.setTitle("Remove Account");

                builder.setMessage("Are you sure to remove your account? Removed account can't be return");

                builder.setPositiveButton("YES", (dialog, which) -> {
                    sessionManager.setLogin(false);
                    sessionManager.setId(0);
                    Toast.makeText(Profile.this, "Account Has Been Removed",
                            Toast.LENGTH_SHORT).show();
                    JobseekerRemoveRequest jobseekerRemoveRequest = new JobseekerRemoveRequest(String.valueOf(jobseekerId), removeListener);
                    RequestQueue queue = Volley.newRequestQueue(Profile.this);
                    queue.add(jobseekerRemoveRequest);

                    Intent intent = new Intent(Profile.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                });

                builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

                builder.show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button logout is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);

                builder.setTitle("Logout");

                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", (dialog, which) -> {
                    sessionManager.setLogin(false);
                    sessionManager.setId(0);
                    Toast.makeText(Profile.this, "Logout Successful",
                            Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    Intent intent = new Intent(Profile.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAffinity();
                });

                builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

                builder.show();

            }
        });
    }

    /**
     * Method when menu drawer is pause
     */
    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.closeDrawer(drawerLayout);

    }
}