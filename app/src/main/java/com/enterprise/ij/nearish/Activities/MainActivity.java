package com.enterprise.ij.nearish.Activities;

import android.app.FragmentManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.enterprise.ij.nearish.Fragments.CategoryList;
import com.enterprise.ij.nearish.Fragments.MapViewFragment;
import com.enterprise.ij.nearish.Fragments.PlaceDetails;
import com.enterprise.ij.nearish.Fragments.PlacesList;
import com.enterprise.ij.nearish.Models.Place;
import com.enterprise.ij.nearish.Models.Usuario;
import com.enterprise.ij.nearish.Other.DownloadUrl;
import com.enterprise.ij.nearish.Other.GetNearbyPlacesData;
import com.enterprise.ij.nearish.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.enterprise.ij.nearish.Fragments.ImportFragment;
import com.enterprise.ij.nearish.Fragments.MainFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    PlacesList placesList;
    MapViewFragment sMapFragment;
    CategoryList categoryList;
    android.support.v4.app.FragmentManager sFm;
    boolean isMapFragment = false;
    String googlePlacesData = "";
    FloatingActionButton fab = null;
    final String serverUrl = "http://35.197.5.57:9000/users/";
    String rootStr = "/places/?";
    String latStr = "lat=";
    String lngStr = "&lng=";
    String url = "";
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sMapFragment = new MapViewFragment();
        categoryList = new CategoryList();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra("id");
        Log.d("IIIIIIIIID", id);
        String email = getIntent().getStringExtra("email");
        String[] emailParts = email.split("@");
        String name = emailParts[0];

        user = new Usuario();
        user.setToken(id);
        user.setemail(email);
        user.setname(name);
        rootStr = id + rootStr;

        sFm = getSupportFragmentManager();
        placesList = new PlacesList();
        if (!placesList.isAdded())
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        else
            sFm.beginTransaction().show(sMapFragment).commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMapFragment){
                    fab.setEnabled(false);
                    sFm.beginTransaction().replace(R.id.map, placesList).commit();
                    isMapFragment = false;
                }
                else {
                    sFm.beginTransaction().replace(R.id.map, sMapFragment).addToBackStack(null).commit();
                    isMapFragment = true;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (id == R.id.nav_recommended_list) {
            if(!placesList.isAdded()){
                sFm.beginTransaction().replace(R.id.map, placesList).commit();
            }
        }

        if (id == R.id.nav_categories) {
            if(!categoryList.isAdded()) {
                sFm.beginTransaction().replace(R.id.map, categoryList).commit();
            }
        }

        //else if (id == R.id.nav_gallery) {

         //   if (!sMapFragment.isAdded())
         //       sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
         //   else
         //       sFm.beginTransaction().show(sMapFragment).commit();

        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void displayDetails(String id) {
        PlaceDetails placeDetails = new PlaceDetails();
        Bundle args = new Bundle();
        args.putString("id", id);
        placeDetails.setArguments(args);
        sFm.beginTransaction().replace(R.id.map, placeDetails).addToBackStack(null).commit();
    }

    public String getGooglePlacesData() {
        return googlePlacesData;
    }

    public Place getGooglePlace(String id) {
        googlePlacesData = getGooglePlacesData();
        Gson gson = new Gson();
        //DataParser dataParser = new DataParser();
        //List<HashMap<String, String>> list = dataParser.parse(googlePlacesData);
        Place[] placesArray = gson.fromJson(googlePlacesData, Place[].class);
        for (Place place : placesArray) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }

    public void obtainGooglePlaces() {
        Object[] dataTransfer = new Object[1];
        url = serverUrl + rootStr + latStr + lngStr;
        dataTransfer[0] = url;
        Log.d("URLLLLL", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        try {
            googlePlacesData = getNearbyPlacesData.execute(dataTransfer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void enableFabButton() {
        fab.setEnabled(true);
    }

    public void setLatStr(Double lat) {
        this.latStr = "lat=" + lat;
    }

    public void setLngStr(Double lng) {
        this.lngStr = "&lng=" + lng;
    }

    public String getUserId() {
        return this.user.getToken();
    }
}
