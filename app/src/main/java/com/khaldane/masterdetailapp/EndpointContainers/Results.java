package com.khaldane.masterdetailapp.EndpointContainers;

/**
 * Created by Kat on 15-12-30.
 */
public class Results {

    int listing_id;
    String state;
    int user_id;
    int category_id;
    String title;
    String description;
    String price;
    String currency_mode;
    int views;
    int num_favorers;
    int quantity;
    String tags[];
    String materials[];
    String url;
    ListingImage MainImage;

    public Results() {
    }

    public Results(int listing_id, String state, int user_id, int category_id, String title, String description, String price, String currency_mode, int quantity, String[] tags, String[] materials, String url, int views, int num_favorers, ListingImage MainImage) {
        this.listing_id = listing_id;
        this.state = state;
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.currency_mode = currency_mode;
        this.quantity = quantity;
        this.tags = tags;
        this.materials = materials;
        this.url = url;
        this.views = views;
        this.num_favorers = num_favorers;
        this.MainImage = MainImage;
    }

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency_mode() {
        return currency_mode;
    }

    public void setCurrency_mode(String currency_mode) {
        this.currency_mode = currency_mode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getMaterials() {
        return materials;
    }

    public void setMaterials(String[] materials) {
        this.materials = materials;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getNum_favorers() {
        return num_favorers;
    }

    public void setNum_favorers(int num_favorers) {
        this.num_favorers = num_favorers;
    }

    public ListingImage getMainImage() {
        return MainImage;
    }

    public void setMainImage(ListingImage mainImage) {
        this.MainImage = mainImage;
    }
}
