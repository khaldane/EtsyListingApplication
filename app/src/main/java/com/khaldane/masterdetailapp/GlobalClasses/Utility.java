package com.khaldane.masterdetailapp.GlobalClasses;

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

    /*
     * Saves a string to shared preferences
     * @params String, String, Context
     */
    public static void saveToSharedPreferences(String key, String value ,Context c) {
        String SHARED_PREFS = "com.khaldane.masterdetailapp";
        SharedPreferences preferences = c.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    /*
     * Instantiates gson builder
     * @return Gson
     */
    public static Gson gsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();

        return builder.create();
    }

    /*
     * Parses a string to a ListingDetailsDisplay
     * @params String
     * @return ListingDetialsDisplay
     */
    public static ListingDetailsDisplay parseListingDetails(String parse) {
        if(parse.equals("")) {
            return new ListingDetailsDisplay();
        } else {
            Gson gson = new Gson();
            JsonObject t = (new JsonParser()).parse(parse).getAsJsonObject();
            return gson.fromJson(t, ListingDetailsDisplay.class);
        }
    }

    /*
     * Parses item results
     * @params String
     * @return Results
     */
    public static Results parseResults (String parse) {
        if(parse.equals("")) {
            return new Results();
        } else {
            Gson gson = new Gson();
            JsonObject t = (new JsonParser()).parse(parse).getAsJsonObject();
            return gson.fromJson(t, Results.class);
        }
    }

    /*
     * Parses array to a string
     * @params String[]
     * @return String
     */
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

    /*
     * Strips nulls from a result set
     * @params Result[]
     * @return ArrayList<Results>
     */
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
