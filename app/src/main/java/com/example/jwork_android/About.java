package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class for About Page
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class About extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu, btProfile;
    RecyclerView recyclerView;

    private static int jobseekerId;

    /**
     * Method when About Page is created
     * @param savedInstanceState Instance's State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);
        btProfile = findViewById(R.id.bt_profile);

        TextView textHeaderAbout = findViewById(R.id.text_view_about_header);
        TextView textBodyAbout = findViewById(R.id.text_view_about_body);
        TextView textContactAbout = findViewById(R.id.text_view_about_contact);
        TextView textLinkEmailAbout = findViewById(R.id.text_view_about_link_email);
        TextView textLinkLinkedinAbout = findViewById(R.id.text_view_about_link_linkedin);
        TextView textLinkInstagramAbout = findViewById(R.id.text_view_about_link_instagram);

        textHeaderAbout.setText(R.string.text_header_about);
        textBodyAbout.setText(R.string.text_body_about);
        textContactAbout.setText(R.string.text_contact_about);
        textLinkEmailAbout.setText(Html.fromHtml(getResources().getString(R.string.email_link)));
        textLinkLinkedinAbout.setText(Html.fromHtml(getResources().getString(R.string.linkedin_link)));
        textLinkInstagramAbout.setText(Html.fromHtml(getResources().getString(R.string.instagram_link)));
        Linkify.addLinks(textLinkEmailAbout, Linkify.ALL);
        Linkify.addLinks(textLinkLinkedinAbout, Linkify.ALL);
        Linkify.addLinks(textLinkInstagramAbout, Linkify.ALL);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            jobseekerId = extras.getInt("jobseekerId");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter(this, MainActivity.arrayList));

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
                Intent intent = new Intent(About.this, Profile.class);
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
        MainActivity.closeDrawer(drawerLayout);
    }
}