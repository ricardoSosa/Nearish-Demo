package com.enterprise.ij.nearish.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.enterprise.ij.nearish.Other.DownloadUrl;

import java.io.IOException;

/**
 * Created by juan on 26/06/2017.
 */

public class Place {
    private String id;
    private String name;
    private String vicinity;
    private String lat;
    private String lng;
    private String photo_reference;
    private float rating;

    public Place(String id, String name, String vicinity, float rating, String photo_reference, String lat, String lng) throws IOException {
        this.id = id;
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.photo_reference = photo_reference;
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

    public String getPhotoReference() {
        return photo_reference;
    }

    public void setPhotoReference(String photo_reference) {
        this.photo_reference = photo_reference;
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