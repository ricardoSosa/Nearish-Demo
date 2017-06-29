package com.enterprise.ij.nearish.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by juan on 26/06/2017.
 */

public class Place {
    private int id;
    private String name;
    private String description;
    private float rating;
    private Drawable image;

    Place() {

    }

    public Place(String name, String description, float rating, Drawable image) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public Place(int id, String name, String description, float rating, Drawable image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}