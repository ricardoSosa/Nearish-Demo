package com.enterprise.ij.nearish.Models;

/**
 * Created by RIKI on 01/07/2017.
 */

public class Rating {
    private String place_id;
    private String user_id;
    private String rating;

    public Rating(String place_id, String user_id, String rating) {
        this.place_id = place_id;
        this.user_id = user_id;
        this.rating = rating;
    }

    public String getPlaceId() {
        return place_id;
    }

    public void setPlaceId(String place_id) {
        this.place_id = place_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
