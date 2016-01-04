package com.khaldane.masterdetailapp.EndpointContainers;

import retrofit.http.GET;
import retrofit.http.Query;

public interface Etsy {

    //Get Featured Listings
    @GET("/v2/featured_treasuries/listings/?api_key=sifux1jmywuercksb4d8cdum&includes=MainImage")
    ListingDetails getFeaturedListings(@Query("page") int page);

    //Get Trending Listings
    @GET("/v2/listings/trending?api_key=sifux1jmywuercksb4d8cdum&includes=MainImage")
    ListingDetails getTrendingListings(@Query("page") int page);

    //Get Active Listing
    @GET("/v2/listings/active?api_key=sifux1jmywuercksb4d8cdum&includes=MainImage")
    ListingDetails getActiveListings(@Query("page") int page);

    //Search Listings
    @GET("/v2/listings/active?api_key=sifux1jmywuercksb4d8cdum&includes=MainImage")
    ListingDetails getSearchQuery(@Query("page") int page, @Query("keywords") String keywords);

}
