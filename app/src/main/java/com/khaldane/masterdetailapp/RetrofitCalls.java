package com.khaldane.masterdetailapp;

import android.content.Context;

import com.google.gson.Gson;
import com.khaldane.masterdetailapp.EndpointContainers.Etsy;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetailsDisplay;
import com.khaldane.masterdetailapp.EndpointContainers.Results;

import java.util.ArrayList;

import retrofit.RestAdapter;


public class RetrofitCalls {

    static final String ENDPOINT = "https://openapi.etsy.com";

    static final RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(ENDPOINT)
            .build();

    static final Etsy sService = adapter.create(Etsy.class);


    public static ListingDetailsDisplay getFeaturedListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails featured = sService.getFeaturedListings(page);
        ListingDetailsDisplay featuredDisplay = new ListingDetailsDisplay();
        ArrayList<Results> containsNulls = Utility.stripNulls(featured.getResults());
        featuredDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("featured", gson.toJson(featuredDisplay), c);

        return featuredDisplay;
    }

    public static ListingDetailsDisplay getTrendingListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetailsDisplay trendingDisplay = new ListingDetailsDisplay();
        ListingDetails trending = sService.getTrendingListings(page);
        ArrayList<Results>  containsNulls = Utility.stripNulls(trending.getResults());
        trendingDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("trending", gson.toJson(trendingDisplay), c);

        return trendingDisplay;
    }

    public static ListingDetailsDisplay getActiveListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails active = sService.getActiveListings(page);
        ListingDetailsDisplay activeDisplay = new ListingDetailsDisplay();
        ArrayList<Results>  containsNulls = Utility.stripNulls(active.getResults());
        activeDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("active", gson.toJson(activeDisplay), c);

        return activeDisplay;
    }

}
