package com.enterprise.ij.nearish.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.enterprise.ij.nearish.Adapters.LikablePlacesAdapter;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.Models.Usuario;
import com.enterprise.ij.nearish.Other.DownloadUrl;
import com.enterprise.ij.nearish.Other.GetNearbyPlacesData;
import com.enterprise.ij.nearish.Other.ServiceHandler;
import com.enterprise.ij.nearish.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class GettingToKnowYou extends AppCompatActivity {

    GridView placesList;
    ArrayList<Place> places = new ArrayList<>();
    String googlePlacesData;
    String url = "http://35.197.5.57:9000/places/random";
    ArrayList<Place> selectedPlaces = new ArrayList<>();
    int selectedPlacesQuantity=0;
    private UserLikablePlaceTask userLikablePlaceTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_to_know_you);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPlacesQuantity>=5){
                    userLikablePlaceTask = new UserLikablePlaceTask();
                    userLikablePlaceTask.execute();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", getIntent().getStringExtra("id"));
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    startActivity(intent);
                }else {
                    int placesLeft = 5- selectedPlacesQuantity;
                    Snackbar.make(view, "At least select "+placesLeft+" places more to continue", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        placesList = (GridView) findViewById(R.id.likablePlacesList);

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

        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Place selectedPlace = places.get(position);
                ImageView selectedImage = (ImageView) v.findViewById(R.id.likablePlaceImage);

                if(!isPlaceSelected(selectedPlace)){
                    selectedImage.setColorFilter(ContextCompat.getColor(v.getContext(),R.color.DeepSkyBlue), PorterDuff.Mode.MULTIPLY);
                    selectedImage.setBackgroundResource(R.drawable.border_place);
                    selectedPlaces.add(selectedPlace);
                    selectedPlacesQuantity++;
                }else{
                    selectedImage.clearColorFilter();
                    selectedImage.setBackgroundResource(0);
                    selectedPlaces.remove(selectedPlace);
                    selectedPlacesQuantity--;
                }
            }
        });
    }

    private boolean isPlaceSelected(Place selectedPlace){
        for (Place aPlace:selectedPlaces) {
            if(aPlace.getId().equals(selectedPlace.getId())){
                return true;
            }
        }
        return false;
    }

    class UserLikablePlaceTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            for (Place aSelectedPlace:selectedPlaces) {
                serviceHandler.executePostLikablePlaces(Usuario.getUserInstance().getToken(),aSelectedPlace.getId());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            userLikablePlaceTask=null;
        }

    }
}
