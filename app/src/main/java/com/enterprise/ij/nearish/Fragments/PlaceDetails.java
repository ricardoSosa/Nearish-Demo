package com.enterprise.ij.nearish.Fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.ij.nearish.Activities.MainActivity;
import com.enterprise.ij.nearish.Fragments.PlaceDetailsFragment;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.R;

public class PlaceDetails extends Fragment {

    private Place place;
    private RatingBar ratingBar;
    private RatingBar placeRatingBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_place_details, container, false);

        String id = getArguments().getString("id");

        place = ((MainActivity) getActivity()).getGooglePlace(id);

        TextView placeDetailsName = (TextView) rootView.findViewById(R.id.placeDetailsName);
        placeDetailsName.setText(place.getName());

        TextView placeDetailsVicinity = (TextView) rootView.findViewById(R.id.placeDetailsVicinity);
        placeDetailsVicinity.setText(place.getVicinity());

        TextView placeDetailsRatingNumber = (TextView) rootView.findViewById(R.id.placeDetailsRatingNumber);
        placeDetailsRatingNumber.setText("" + place.getRating());

        changeStarsColor(rootView, place.getRating());

        PlaceDetailsFragment placeDetailsFragment = new PlaceDetailsFragment();

        FragmentManager sfm = getChildFragmentManager();

        sfm.beginTransaction().add(R.id.placeDetailsMap, placeDetailsFragment).commit();

        return rootView;
    }

    public Place getPlace() {
        return place;
    }

    /**
     * Display rating by calling getRating() method.
     * @param view
     */
    public void rateMe(View view){

        Toast.makeText(getActivity().getApplicationContext(),
                String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
    }

    private void changeStarsColor(View rootView, float rating){
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        placeRatingBar = (RatingBar) rootView.findViewById(R.id.placeRatingBar);

        final int GOLD = ContextCompat.getColor(getActivity(), R.color.Gold);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);


        LayerDrawable starsPlace = (LayerDrawable) placeRatingBar.getProgressDrawable();
        starsPlace.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);

        placeRatingBar.setRating(rating);
    }
}