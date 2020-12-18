package com.innova.rent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setRole(String role) {
        prefs.edit().putString("role", role).commit();
    }

    public String getRole() {
        String role = prefs.getString("role","");
        return role;
    }
}
