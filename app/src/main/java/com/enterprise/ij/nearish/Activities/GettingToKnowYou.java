package com.enterprise.ij.nearish.Activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.enterprise.ij.nearish.Adapters.LikablePlacesAdapter;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.Other.DownloadUrl;
import com.enterprise.ij.nearish.Other.GetNearbyPlacesData;
import com.enterprise.ij.nearish.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class GettingToKnowYou extends ListActivity {

    ListView placesList;
    ArrayList<Place> places = new ArrayList<>();
    String googlePlacesData;
    String url = "http://35.197.5.57:9000/places/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_to_know_you);

        placesList = (ListView) findViewById(android.R.id.list);

        Object[] dataTransfer = new Object[1];
        dataTransfer[0] = url;
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        try {
            googlePlacesData = getNearbyPlacesData.execute(dataTransfer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Place[] placesArray = gson.fromJson(googlePlacesData, Place[].class);
        Collections.addAll(places, placesArray);

        LikablePlacesAdapter adapter = new LikablePlacesAdapter(this,places);
        placesList.setAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }
}
