package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;

    /**
     * method for refreshList
     */
    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            String city = location.getString("city");
                            String province = location.getString("province");
                            String description = location.getString("description");

                            Location locationCS9 = new Location(province, city, description);

                            int recruiterId = recruiter.getInt("id");
                            String recruiterName = recruiter.getString("name");
                            String recruiterEmail = recruiter.getString("email");
                            String recruiterPhoneNumber = recruiter.getString("phoneNumber");

                            Recruiter recruiterObject = new Recruiter(recruiterId,
                                    recruiterName, recruiterEmail, recruiterPhoneNumber, locationCS9);
                            if (listRecruiter.size() > 0) {
                                boolean success = true;
                                for (Recruiter rec : listRecruiter)
                                    if (rec.getId() == recruiterObject.getId())
                                        success = false;
                                if (success) {
                                    listRecruiter.add(recruiterObject);
                                }
                            } else {
                                listRecruiter.add(recruiterObject);
                            }

                            int jobId = job.getInt("id");
                            String jobName = job.getString("name");
                            int jobFee = job.getInt("fee");
                            String jobCategory = job.getString("category");

                            Job jobObject = new Job(jobId, jobName, recruiterObject, jobFee, jobCategory);
                            jobIdList.add(jobObject);

                            for (Recruiter rec : listRecruiter) {
                                ArrayList<Job> temp = new ArrayList<>();
                                for (Job job2 : jobIdList) {
                                    if (job2.getRecruiter().getName().equals(rec.getName()) ||
                                            job2.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                            job2.getRecruiter().getPhoneNumber()
                                                    .equals(rec.getPhoneNumber())) {
                                        temp.add(job2);
                                    }
                                }
                                childMapping.put(rec, temp);
                            }
                        }
                        expandableListAdapter = new MainListAdapter(MainActivity.this,
                                listRecruiter, childMapping);
                        expandableListView.setAdapter(expandableListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

    /**
     * method for create Menu Page
     * @param savedInstanceState saveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);

        refreshList();
    }
}