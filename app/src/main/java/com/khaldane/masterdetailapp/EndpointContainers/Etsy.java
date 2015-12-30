package com.khaldane.masterdetailapp.EndpointContainers;

import retrofit.Callback;
import retrofit.http.GET;

public interface Etsy {

    //Get Featured Listings - https://openapi.etsy.com/v2/featured_treasuries/listings/homepage_current?api_key=sifux1jmywuercksb4d8cdum
    @GET("/v2/featured_treasuries/listings/homepage_current?api_key=sifux1jmywuercksb4d8cdum")
    ListingDetails getFeaturedListings();
    //void getFeaturedListings(Callback<ListingDetails> response);

    //Get Trending Listings - https://openapi.etsy.com/v2/listings/trending?api_key=sifux1jmywuercksb4d8cdum
    @GET("/v2/listings/trending?api_key=sifux1jmywuercksb4d8cdum")
    ListingDetails getTrendingListings();

    //Get Active Listings - https://openapi.etsy.com/v2/listings/active?api_key=sifux1jmywuercksb4d8cdum
    @GET("/v2/listings/active?api_key=sifux1jmywuercksb4d8cdum")
    ListingDetails getActiveListings();

    //Search Listings -

    //Get Shop Details - https://openapi.etsy.com/v2/shops/:shop_id?api_key=sifux1jmywuercksb4d8cdum
    @GET("/v2/shops/:shop_id?api_key=sifux1jmywuercksb4d8cdum")
    void getShopDetails(Callback<ShopDetails> response);

    //Item Image -

    //Shop Image -
}
