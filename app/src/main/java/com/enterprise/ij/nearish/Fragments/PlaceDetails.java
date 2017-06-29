package com.enterprise.ij.nearish.Fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.enterprise.ij.nearish.Fragments.PlaceDetailsFragment;
import com.enterprise.ij.nearish.R;

public class PlaceDetails extends FragmentActivity {

    private RatingBar ratingBar;
    private RatingBar placeRatingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        PlaceDetailsFragment placeDetailsFragment = new PlaceDetailsFragment();

        FragmentManager sfm = getSupportFragmentManager();

        sfm.beginTransaction().add(R.id.placeDetailsMap, placeDetailsFragment).commit();

        changeStarsColor();



    }

    /**
     * Display rating by calling getRating() method.
     * @param view
     */
    public void rateMe(View view){

        Toast.makeText(getApplicationContext(),
                String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
    }

    private void changeStarsColor(){
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        placeRatingBar = (RatingBar) findViewById(R.id.placeRatingBar);

        final int GOLD = ContextCompat.getColor(this, R.color.Gold);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);


        LayerDrawable starsPlace = (LayerDrawable) placeRatingBar.getProgressDrawable();
        starsPlace.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);

        placeRatingBar.setRating(3.5f);
    }
}