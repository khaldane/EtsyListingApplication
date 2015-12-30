package com.khaldane.masterdetailapp;

import android.content.Context;

import com.google.gson.Gson;
import com.khaldane.masterdetailapp.EndpointContainers.Etsy;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;

import retrofit.RestAdapter;


public class RetrofitCalls {

    static final String ENDPOINT = "https://openapi.etsy.com";

    static final RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(ENDPOINT)
            .build();

    static final Etsy sService = adapter.create(Etsy.class);


    public static ListingDetails getFeaturedListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails featured = sService.getFeaturedListings(page);
        Utility.saveToSharedPreferences("featured", gson.toJson(featured), c);

        return featured;
    }

    public static ListingDetails getTrendingListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails trending = sService.getTrendingListings(page);
        Utility.saveToSharedPreferences("trending", gson.toJson(trending), c);

        return trending;
    }

    public static ListingDetails getActiveListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails active = sService.getActiveListings(page);
        Utility.saveToSharedPreferences("active", gson.toJson(active), c);

        return active;
    }

}
