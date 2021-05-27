package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for MainActivity
 *
 * @author Ivan Widjanarko
 * @version 27-05-2021
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList <Job>> childMapping = new HashMap<>();

    /**
     * method for create Menu Page
     * @param savedInstanceState saveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ExpandableListView elvList = findViewById(R.id.lvExp);

        refreshList();

        elvList.setAdapter(new MainListAdapter(MainActivity.this, listRecruiter, childMapping));
    }

    /**
     * method for refreshList
     */
    protected void refreshList() {
        Response.Listener<String>  responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Recruiter recruiterObject =
                                    new Recruiter(
                                        recruiter.getInt("id"),
                                        recruiter.getString("name"),
                                        recruiter.getString("email"),
                                        recruiter.getString("phoneNumber"),
                                            new Location (location.getString("province"),
                                                location.getString("city"),
                                                location.getString("description")));

                            listRecruiter.add(recruiterObject);

                            Job jobObject =
                                    new Job(
                                        job.getInt("id"),
                                        job.getString("name"),
                                        recruiterObject,
                                        job.getInt("fee"),
                                        job.getString("Category")
                                    );

                            jobIdList.add(jobObject);
                            }

                        for (Recruiter rec : listRecruiter) {
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job job2 : jobIdList) {
                                if (job2.getRecruiter().getName().equals(rec.getName()) ||
                                    job2.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                    job2.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber()))
                                {
                                    temp.add(job2);
                                }
                            }
                            childMapping.put(rec, temp);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,
                            "Menu Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}