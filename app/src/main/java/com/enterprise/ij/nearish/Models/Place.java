package com.enterprise.ij.nearish.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by juan on 26/06/2017.
 */

public class Place {
    private String id;
    private String name;
    private String vicinity;
    private String lat;
    private String lng;
    private float rating;
    private Drawable image;

    public Place(String name, String vicinity, float rating, Drawable image, String lat, String lng) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.image = image;
    }

    public Place(String id, String name, String vicinity, float rating, Drawable image, String lat, String lng) {
        this.id = id;
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void seVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lat) {
        this.lng = lng;
    }
}