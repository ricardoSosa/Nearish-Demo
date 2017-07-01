package com.enterprise.ij.nearish.Models;

/**
 * Created by RIKI on 01/07/2017.
 */

public class UserLikablePlace {
    private String place_id;
    private String user_id;
    private String rating;

    public UserLikablePlace(String place_id, String user_id, String rating) {
        this.place_id = place_id;
        this.user_id = user_id;
        this.rating = rating;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "UserLikablePlace{" +
                "place_id=" + place_id +
                ", user_id='" + user_id + '\'' +
                ", rating=" + rating +
                '}';
    }
}
