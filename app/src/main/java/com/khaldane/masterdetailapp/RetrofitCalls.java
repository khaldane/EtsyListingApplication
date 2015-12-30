package com.khaldane.masterdetailapp;

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


    public static ListingDetails getFeaturedListings() {

        return sService.getFeaturedListings();
    }

    public static ListingDetails getTrendingListings() {

        return sService.getTrendingListings();
    }

    public static ListingDetails getActiveListings() {

        return sService.getActiveListings();
    }
}
