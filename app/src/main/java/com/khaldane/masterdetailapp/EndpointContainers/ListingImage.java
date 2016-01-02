package com.khaldane.masterdetailapp.EndpointContainers;

/**
 * Created by Kat on 16-01-02.
 */
public class ListingImage {
    String url_75x75;
    String url_170x135;
    String url_570xN;

    public ListingImage() {
    }

    public ListingImage(String url_75x75, String url_170x135, String url_570xN) {
        this.url_75x75 = url_75x75;
        this.url_170x135 = url_170x135;
        this.url_570xN = url_570xN;
    }

    public String getUrl_75x75() {
        return url_75x75;
    }

    public void setUrl_75x75(String url_75x75) {
        this.url_75x75 = url_75x75;
    }

    public String getUrl_170x135() {
        return url_170x135;
    }

    public void setUrl_170x135(String url_170x135) {
        this.url_170x135 = url_170x135;
    }

    public String getUrl_570xN() {
        return url_570xN;
    }

    public void setUrl_570xN(String url_570xN) {
        this.url_570xN = url_570xN;
    }
}
