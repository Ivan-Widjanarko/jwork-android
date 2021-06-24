package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class for Main Activity
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu, btProfile;
    RecyclerView recyclerView;
    static ArrayList<String> arrayList = new ArrayList<>();
    MainAdapter adapter;
    private static int jobseekerId;

    private boolean isPressed = false;

    /**
     * Method for close the menu drawer
     * @param drawerLayout Drawer's Layout
     */
    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

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
     * Method when Main Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);
        btProfile = findViewById(R.id.bt_profile);

        arrayList.clear();

        arrayList.add("Home");
        arrayList.add("About");
        arrayList.add("Logout");
        arrayList.add("Exit");

        adapter = new MainAdapter(this, arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
                Intent intent = new Intent(MainActivity.this, Profile.class);
                intent.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
            }
        });

        Button btnHistoryHome = findViewById(R.id.btnHistoryHome);
        btnHistoryHome.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button applied job is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinishedJobActivity.class);
                intent.putExtra("jobseekerId", jobseekerId);

                startActivity(intent);
            }
        });

        Button btnApplyJobHome = findViewById(R.id.btnApplyJobHome);
        btnApplyJobHome.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when button apply job is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("jobseekerId", jobseekerId);

                startActivity(intent);
            }
        });
    }

    /**
     * Method when menu drawer is pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}