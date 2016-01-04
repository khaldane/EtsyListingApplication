package com.khaldane.masterdetailapp.EndpointContainers;

public class Results {

    String title;
    String description;
    String price;
    int views;
    int num_favorers;
    int quantity;
    String tags[];
    String materials[];
    ListingImage MainImage;

    public Results() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String[] getTags() {
        return tags;
    }

    public String[] getMaterials() {
        return materials;
    }

    public int getViews() {
        return views;
    }

    public int getNum_favorers() {
        return num_favorers;
    }

    public ListingImage getMainImage() {
        return MainImage;
    }

}
