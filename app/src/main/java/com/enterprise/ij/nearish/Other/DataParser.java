package com.enterprise.ij.nearish.Other;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonResult = null;

        try {
            Log.d("Places", jsonData);
            jsonResult = new JSONArray(jsonData);
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return getPlaces(jsonResult);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String main_type = "-NA-";
        String latitude = "";
        String longitude = "";

        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if (!googlePlaceJson.isNull("types")) {
                String[] typesArray = googlePlaceJson.getString("types").split(",");
                Boolean chosen_type = false;
                for(int i = 0; i < typesArray.length; i++){
                    String type = typesArray[i];
                    switch(type){
                        case "restaurant":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "cafe":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "bar":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "bakery":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "casino":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "shopping_mall":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "convenience_store":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "meal_delivery":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "meal_takeaway":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "store":
                            main_type = type;
                            chosen_type = true;
                            break;

                        case "night_club":
                            main_type = type;
                            chosen_type = true;
                            break;
                    }
                    if (chosen_type) break;
                }
            }
            latitude = googlePlaceJson.getString("lat");
            longitude = googlePlaceJson.getString("lng");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("main_type", main_type);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}