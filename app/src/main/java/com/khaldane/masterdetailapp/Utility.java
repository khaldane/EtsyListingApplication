package com.khaldane.masterdetailapp;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;

public class Utility {

    public static void saveToSharedPreferences(String key, String value ,Context c) {
        String SHARED_PREFS = "com.khaldane.masterdetailapp";
        SharedPreferences preferences = c.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static Gson gsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();

        return builder.create();
    }

    public static ListingDetails parseListingDetails(String parse) {
        if(parse.equals("")) {
            return new ListingDetails();
        } else {
            Gson gson = new Gson();
            JsonObject t = (new JsonParser()).parse(parse).getAsJsonObject();
            return gson.fromJson(t, ListingDetails.class);
        }
    }
}
