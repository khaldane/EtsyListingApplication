package com.khaldane.masterdetailapp;


import android.content.Context;
import android.content.SharedPreferences;

public class Utility {

    public static void saveToSharedPreferences(String key, String value ,Context c) {
        String sharedPrefs = "com.khaldane.masterdetailapp";
        SharedPreferences preferences = c.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
    }
}
