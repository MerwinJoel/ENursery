package com.enursery.rit;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    public static final String PREFS_NAME = "PLANT_PREFS";

    public SharedPreference() {
        super();
    }

    public void save(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(key, value); //3

        editor.commit(); //4
    }

    public registration_supplier getUser(Context context) {
        registration_supplier user = new registration_supplier();
        user.setEmail(getValue(context, "email"));
        user.setUname(getValue(context, "name"));
        user.setUsertype(getValue(context, "usertype"));
        user.setUserid(getValue(context, "userid"));
        return user;
    }

    public String getValue(Context context, String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, "");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context, String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.commit();
    }
}