package com.khaldane.masterdetailapp;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetailsDisplay;
import com.khaldane.masterdetailapp.EndpointContainers.Results;

import java.util.ArrayList;

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

    public static ListingDetailsDisplay parseListingDetails(String parse) {
        if(parse.equals("")) {
            return new ListingDetailsDisplay();
        } else {
            Gson gson = new Gson();
            JsonObject t = (new JsonParser()).parse(parse).getAsJsonObject();
            return gson.fromJson(t, ListingDetailsDisplay.class);
        }
    }

    public static Results parseResults (String parse) {
        if(parse.equals("")) {
            return new Results();
        } else {
            Gson gson = new Gson();
            JsonObject t = (new JsonParser()).parse(parse).getAsJsonObject();
            return gson.fromJson(t, Results.class);
        }
    }

    public static String parseArray (String[] arr) {
        StringBuilder builder = new StringBuilder();
            if(arr != null) {
            for (String s : arr) {
                builder.append(s.substring(0, 1).toUpperCase() + s.substring(1));
                builder.append(", ");
            }
            return builder.length() > 0 ? builder.substring(0, builder.length() - 2): "";
        } else {
            return "none";
        }
    }

    public static ArrayList<Results> stripNulls(Results[] results) {
        ArrayList<Results> mNewResultsList = new ArrayList<>();
        for (Results r : results) {
            if (r.getTitle() != null && !r.getTitle().isEmpty() && r.getPrice() != null && !r.getPrice().isEmpty()) {
                mNewResultsList.add(r);
            }
        }
        return mNewResultsList;
    }
}
