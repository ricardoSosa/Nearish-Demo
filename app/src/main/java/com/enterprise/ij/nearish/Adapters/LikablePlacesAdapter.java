package com.enterprise.ij.nearish.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.Other.ServiceHandler;
import com.enterprise.ij.nearish.R;

import java.util.ArrayList;

/**
 * Created by juan on 29/06/2017.
 */

public class LikablePlacesAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Place> places;
    protected ArrayList<Place> selectedPlaces = new ArrayList<>();

    public LikablePlacesAdapter(Activity activity, ArrayList<Place> places) {
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

        LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inf.inflate(R.layout.likable_place_item, null);

        Place dir = places.get(position);

        TextView name = (TextView) v.findViewById(R.id.placeName);
        name.setText(dir.getName());

        ImageView placeImage = (ImageView) v.findViewById(R.id.likablePlaceImage);

        if (dir.getPhotoReference() == null) {
            placeImage.setImageResource(R.drawable.logo);
        } else {
            String serverUrl = "https://maps.googleapis.com/maps/api/place/photo?";
            String maxWidthStr = "maxwidth=400";
            String refStr = "&photoreference=" + dir.getPhotoReference();
            String keyStr = "&key=AIzaSyDGr1kwH8fKtTpdkMcAIah5kkayMWJdY8U";
            String url = serverUrl + maxWidthStr + refStr + keyStr;
            Glide.with(v.getContext()).load(url).into(placeImage);
        }

        return v;
    }
}
