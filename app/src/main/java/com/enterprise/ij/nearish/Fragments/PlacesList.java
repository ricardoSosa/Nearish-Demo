package com.enterprise.ij.nearish.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.enterprise.ij.nearish.Activities.MainActivity;
import com.enterprise.ij.nearish.Adapters.PlacesAdapter;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.Other.DataParser;
import com.enterprise.ij.nearish.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacesList extends Fragment {

    ListView placesList;
    String googlePlacesData;
    ArrayList<Place> places = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_places_list, container, false);

        placesList = (ListView) rootView.findViewById(R.id.placesList);

        googlePlacesData = ((MainActivity) getActivity()).getGooglePlacesData();
        Gson gson = new Gson();
        DataParser dataParser = new DataParser();
        List<HashMap<String, String>> placesList = dataParser.parse(googlePlacesData);

        PlacesAdapter adapter = new PlacesAdapter(getActivity(),places);

        //placesList.setAdapter(adapter);

        return rootView;
    }
}