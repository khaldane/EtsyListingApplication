package com.khaldane.masterdetailapp.GlobalClasses;

import android.content.Context;

import com.google.gson.Gson;
import com.khaldane.masterdetailapp.EndpointContainers.Etsy;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetails;
import com.khaldane.masterdetailapp.EndpointContainers.ListingDetailsDisplay;
import com.khaldane.masterdetailapp.EndpointContainers.Results;

import java.util.ArrayList;

import retrofit.RestAdapter;


public class EtsyService {

    private static final String ENDPOINT = "https://openapi.etsy.com";

    private static final RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(ENDPOINT)
            .build();

    private static final Etsy sService = adapter.create(Etsy.class);

    /*
     * Gets the featured listing from Etsy
     * @params int, Context
     * @return ListingDetailsDisplay
     */
    public static ListingDetailsDisplay getFeaturedListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails featured = sService.getFeaturedListings(page);
        ListingDetailsDisplay featuredDisplay = new ListingDetailsDisplay();
        ArrayList<Results> containsNulls = Utility.stripNulls(featured.getResults());
        featuredDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("featured", gson.toJson(featuredDisplay), c);

        return featuredDisplay;
    }

    /*
     * Gets the trending listings
     * @params int, Context
     * @return ListingDetailsDisplay
     */
    public static ListingDetailsDisplay getTrendingListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetailsDisplay trendingDisplay = new ListingDetailsDisplay();
        ListingDetails trending = sService.getTrendingListings(page);
        ArrayList<Results>  containsNulls = Utility.stripNulls(trending.getResults());
        trendingDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("trending", gson.toJson(trendingDisplay), c);

        return trendingDisplay;
    }

    /*
     * Get active listings
     * @params int, Context
     * @return ListingDetailsDisplay
     */
    public static ListingDetailsDisplay getActiveListings(int page, Context c) {
        Gson gson = Utility.gsonBuilder();
        ListingDetails active = sService.getActiveListings(page);
        ListingDetailsDisplay activeDisplay = new ListingDetailsDisplay();
        ArrayList<Results>  containsNulls = Utility.stripNulls(active.getResults());
        activeDisplay.setResults(containsNulls);
        Utility.saveToSharedPreferences("active", gson.toJson(activeDisplay), c);

        return activeDisplay;
    }

    /*
     * Gets search query results
     * @params int, String
     * @return ListingDetailsDisplay
     */
    public static ListingDetailsDisplay getSearchQuery(int page, String query) {
        ListingDetails search = sService.getSearchQuery(page, query);
        ListingDetailsDisplay searchDisplay = new ListingDetailsDisplay();
        ArrayList<Results>  containsNulls = Utility.stripNulls(search.getResults());
        searchDisplay.setResults(containsNulls);

        return searchDisplay;
    }

}
