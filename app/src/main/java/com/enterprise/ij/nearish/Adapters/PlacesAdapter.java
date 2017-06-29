package com.enterprise.ij.nearish.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.R;

import java.util.ArrayList;

/**
 * Created by juan on 26/06/2017.
 */

public class PlacesAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Place> places;

    public PlacesAdapter(Activity activity, ArrayList<Place> places) {
        this.activity = activity;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    public void clear() {
        places.clear();
    }

    public void addAll(ArrayList<Place> newPlaces) {
        for (int i = 0; i < newPlaces.size(); i++) {
            places.add(newPlaces.get(i));
        }
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item, null);
        }

        Place dir = places.get(position);

        TextView placeName = (TextView) v.findViewById(R.id.placeName);
        placeName.setText(dir.getName());

        TextView placeDescription = (TextView) v.findViewById(R.id.placeDescription);
        placeDescription.setText(dir.getDescription());

        RatingBar placeRating = (RatingBar) v.findViewById(R.id.placeListRatingBar);
        placeRating.setRating(dir.getRating());

        placeRating = (RatingBar) v.findViewById(R.id.placeListRatingBar);
        final int GOLD = ContextCompat.getColor(v.getContext(), R.color.Gold);
        LayerDrawable stars = (LayerDrawable) placeRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);

        ImageView imagen = (ImageView) v.findViewById(R.id.placeImage);
        imagen.setImageDrawable(dir.getImage());



        return v;
    }
}