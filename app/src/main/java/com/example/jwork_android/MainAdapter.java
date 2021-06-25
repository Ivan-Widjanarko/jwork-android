package com.example.jwork_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Class for Main Adapter
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Activity activity;
    ArrayList<String> arrayList;

    SessionManager sessionManager;

    /**
     * Constructor for MainAdapter
     * @param activity Activity
     * @param arrayList Array List of the Menu
     */
    public MainAdapter (Activity activity, ArrayList<String> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;

    }

    /**
     * Method when View Holder is created
     * @param parent Parent
     * @param viewType View's Type
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer_main, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Method when View Holder is bind
     * @param holder Holder
     * @param position Menu's Position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {

            /**
             * Method when menu's holder is clicked
             * @param v View
             */
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                switch (position) {
                    case 0 :
                        activity.startActivity(new Intent(activity, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case 1 :
                        activity.startActivity(new Intent(activity, About.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;

                    case 2 :
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        builder.setTitle("Logout");

                        builder.setMessage("Are you sure you want to logout?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            /**
                             * Method when button YES option is clicked
                             * @param dialog Dialog Box
                             * @param which Which
                             */
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.setLogin(false);
                                sessionManager.setId(0);
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                activity.finishAffinity();
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            /**
                             * Method when button CANCEL option is clicked
                             * @param dialog Dialog Box
                             * @param which Which
                             */
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.show();
                        break;

                    case 3 :
                        AlertDialog.Builder builderExit = new AlertDialog.Builder(activity);

                        builderExit.setTitle("Exit");

                        builderExit.setMessage("Are you sure to exit and close the app?");

                        builderExit.setPositiveButton("YES", (dialog, which) -> {
                            activity.finishAffinity();
                            System.exit(0);
                        });

                        builderExit.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

                        builderExit.show();
                }
            }
        });

    }

    /**
     * Method to count item in the array list
     */
    @Override
    public int getItemCount() {

        return arrayList.size();

    }

    /**
     * Method to View Holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        /**
         * Constructor for ViewHolder
         * @param itemView Item's View
         */
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
