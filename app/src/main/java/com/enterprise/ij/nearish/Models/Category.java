package com.enterprise.ij.nearish.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by juan on 27/06/2017.
 */

public class Category {
    private String id;
    private String name;
    private int image;
    private boolean checked;

    public Category(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Category(String id, String name, int image) {
        this.id = id;
        this.name = name;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean getChecked() { return checked; }

    public void setChecked(boolean checked) { this.checked = checked; }
}