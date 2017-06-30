package com.enterprise.ij.nearish.Activities;

import android.app.FragmentManager;
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
import com.enterprise.ij.nearish.Fragments.PlacesList;
import com.enterprise.ij.nearish.Other.DownloadUrl;
import com.enterprise.ij.nearish.Other.GetNearbyPlacesData;
import com.enterprise.ij.nearish.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.enterprise.ij.nearish.Fragments.ImportFragment;
import com.enterprise.ij.nearish.Fragments.MainFragment;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    MapViewFragment sMapFragment;
    PlacesList placesList;
    android.support.v4.app.FragmentManager sFm;
    boolean isMapFragment = false;
    String googlePlacesData = "";
    FloatingActionButton fab = null;
    final String urlUser = "http://35.197.5.57:9000/places/random";
    final String url = "http://35.197.5.57:9000/users/594723454362de004b0f3bcb/places/?lat=20.9674&lng=89.5926";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sMapFragment = new MapViewFragment();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Object[] DataTransfer = new Object[1];
        DataTransfer[0] = url;
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        try {
            googlePlacesData = getNearbyPlacesData.execute(DataTransfer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sFm = getSupportFragmentManager();
        placesList = new PlacesList();
        if (!placesList.isAdded())
            sFm.beginTransaction().add(R.id.map, placesList).commit();
        else
            sFm.beginTransaction().show(placesList).commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMapFragment){
                    sFm.beginTransaction().replace(R.id.map, placesList).commit();
                    isMapFragment = false;
                }
                else {
                    fab.setEnabled(false);
                    sFm.beginTransaction().replace(R.id.map, sMapFragment).commit();
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

        if (id == R.id.nav_categories) {
            sFm.beginTransaction().replace(R.id.map, new CategoryList()).commit();
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

    public String getGooglePlacesData() {
        return googlePlacesData;
    }

    public void enableFabButton() {
        fab.setEnabled(true);
    }
}
