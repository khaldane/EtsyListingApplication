package com.khaldane.masterdetailapp;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utility {

    public static void saveToSharedPreferences(String key, String value ,Context c) {
        String sharedPrefs = "com.khaldane.masterdetailapp";
        SharedPreferences preferences = c.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static Gson gsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();

        return builder.create();
    }
}
