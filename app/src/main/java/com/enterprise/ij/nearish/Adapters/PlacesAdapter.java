package com.enterprise.ij.nearish.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.R;

import java.io.IOException;
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

        v.setTag((String) dir.getId());

        ImageView placeImage = (ImageView) v.findViewById(R.id.placeImage);
        String serverUrl = "https://maps.googleapis.com/maps/api/place/photo?";
        String maxWidthStr = "maxwidth=400";
        String refStr = "&photoreference=" + dir.getPhotoReference();
        String keyStr = "&key=AIzaSyDGr1kwH8fKtTpdkMcAIah5kkayMWJdY8U";
        String url = serverUrl + maxWidthStr + refStr + keyStr;
        Glide.with(v.getContext()).load(url).into(placeImage);

        TextView placeName = (TextView) v.findViewById(R.id.placeName);
        placeName.setText(dir.getName());

        TextView placeDescription = (TextView) v.findViewById(R.id.placeDescription);
        placeDescription.setText(dir.getVicinity());

        RatingBar placeRating = (RatingBar) v.findViewById(R.id.placeListRatingBar);
        placeRating.setRating(dir.getRating());

        placeRating = (RatingBar) v.findViewById(R.id.placeListRatingBar);
        final int GOLD = ContextCompat.getColor(v.getContext(), R.color.Gold);
        LayerDrawable stars = (LayerDrawable) placeRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(GOLD, PorterDuff.Mode.SRC_ATOP);

        return v;
    }
}