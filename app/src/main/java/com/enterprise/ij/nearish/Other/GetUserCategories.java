package com.enterprise.ij.nearish.Other;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by RIKI on 01/07/2017.
 */

public class GetUserCategories extends AsyncTask<Object, String, String> {

    String url = "http://35.197.5.57:9000/users/";

    @Override
    protected String doInBackground(Object[] params) {
        String userCategories = "";
        try {
            String userId = (String) params[0];
            url = url + userId + "/categories";
            DownloadUrl downloadUrl = new DownloadUrl();
            userCategories = downloadUrl.readUrl(url);
            Log.d("doInBackGround", userCategories);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return userCategories;
    }
}
