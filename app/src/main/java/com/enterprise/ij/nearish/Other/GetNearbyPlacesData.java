package com.enterprise.ij.nearish.Other;

import android.os.AsyncTask;
import android.util.Log;

import com.enterprise.ij.nearish.Other.DataParser;
import com.enterprise.ij.nearish.Other.DownloadUrl;
import com.enterprise.ij.nearish.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData  extends AsyncTask<Object, String, String> {

    String url;

    @Override
    protected String doInBackground(Object... params) {

        String googlePlacesData = "";
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            url= (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("doInBackGround", googlePlacesData);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
    }
}
