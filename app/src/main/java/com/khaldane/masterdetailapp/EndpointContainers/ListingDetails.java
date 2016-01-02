package com.khaldane.masterdetailapp.EndpointContainers;


public class ListingDetails {
    Results[] results;

    public ListingDetails() {
    }

    public ListingDetails(Results[] results) {
        this.results = results;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
