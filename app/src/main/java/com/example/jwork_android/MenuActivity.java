package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * Class for Menu Activity
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class MenuActivity extends AppCompatActivity {

    private final ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private final ArrayList<Job> jobIdList = new ArrayList<>();
    private final HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;

    private static int jobseekerId;

    /**
     * method for refreshList
     */
    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            /**
             * Method when access response
             * @param response Response
             */
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

                            Location locationObject = new Location(province, city, description);

                            int recruiterId = recruiter.getInt("id");
                            String recruiterName = recruiter.getString("name");
                            String recruiterEmail = recruiter.getString("email");
                            String recruiterPhoneNumber = recruiter.getString("phoneNumber");

                            Recruiter recruiterObject = new Recruiter(recruiterId,
                                    recruiterName, recruiterEmail, recruiterPhoneNumber, locationObject);
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
                        expandableListAdapter = new MenuListAdapter(MenuActivity.this,
                                listRecruiter, childMapping);
                        expandableListView.setAdapter(expandableListAdapter);
                    }
                } catch (JSONException e) {
                    Toast.makeText(MenuActivity.this, "Expandable List View Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
        queue.add(menuRequest);

    }

    /**
     * Method when Menu Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
        }

        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);

        refreshList();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            /**
             * Method when child button in expandable list is clicked
             * @param expandableListView Expandable List View
             * @param v View
             * @param j Parameter j
             * @param k Parameter k
             * @param l Parameter l
             */
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View v, int j, int k, long l) {
                Intent intent = new Intent(MenuActivity.this, ApplyJobActivity.class);
                int jobId = childMapping.get(listRecruiter.get(j)).get(k).getId();
                String jobName = childMapping.get(listRecruiter.get(j)).get(k).getName();
                String jobCategory = childMapping.get(listRecruiter.get(j)).get(k).getCategory();
                int jobFee = childMapping.get(listRecruiter.get(j)).get(k).getFee();

                intent.putExtra("job_id", jobId);
                intent.putExtra("job_name", jobName);
                intent.putExtra("job_category", jobCategory);
                intent.putExtra("job_fee", jobFee);

                intent.putExtra("jobseekerId", jobseekerId);

                startActivity(intent);
                return true;
            }
        });
    }
}