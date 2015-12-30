package com.khaldane.masterdetailapp.EndpointContainers;


public class ListingDetails {
    int count;
    Results[] results;

    public ListingDetails() {
    }

    public ListingDetails(int count, Results[] results) {
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
