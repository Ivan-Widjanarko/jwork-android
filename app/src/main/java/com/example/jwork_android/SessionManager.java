package com.example.jwork_android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for Session Manager
 *
 * @author Ivan Widjanarko
 * @version 25-06-2021
 */
public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructor for SessionManager
     * @param context Context
     */
    public SessionManager (Context context) {
        sharedPreferences = context.getSharedPreferences("Appkey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * Method for set login session
     * @param login Login Status
     */
    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    /**
     * Method for get login session
     * @return Login Status
     */
    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    /**
     * Method for set ID for the session key
     * @param id Jobseeker's ID
     */
    public void setId(int id) {
        editor.putInt("KEY_ID", id);
        editor.commit();
    }

    /**
     * Method for get ID for the session key
     * @return Jobseeker's ID
     */
    public int getId() {
        return sharedPreferences.getInt("KEY_ID", 0);
    }
}
