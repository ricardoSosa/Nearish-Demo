package com.enterprise.ij.nearish.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by juan on 27/06/2017.
 */

public class Category {
    private int id;
    private String name;
    private Drawable image;

    public Category(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public Category(int id, String name, Drawable image) {
        this.id = id;
        this.name = name;
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

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}