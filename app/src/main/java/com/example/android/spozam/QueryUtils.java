package com.example.android.spozam;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nisarg on 4/16/2017.
 */

public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<String> fetchData(String dataRequested, String accessToken) {

        URL url = createUrl(dataRequested);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url, accessToken);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<String> userPlaylists = fetchRecentlyPlayed(jsonResponse);

        return userPlaylists;
    }

    private static URL createUrl(String dataRequested) {

        URL geratedURL = null;

        try {
            geratedURL = new URL(dataRequested);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error while creating URL " + e.getLocalizedMessage());
        }

        return geratedURL;
    }

    private static String makeHttpRequest(URL url, String accessToken) throws IOException {
        String jsonResponse = "";

        Log.d(LOG_TAG, "URL call to: " + url);
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization","Bearer " + accessToken);
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
               Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<String> fetchRecentlyPlayed(String jsonResponse) {

        List<String> recentlyPlayedList = new ArrayList<>();

        try
        {
            JSONObject paredJsonObject = new JSONObject(jsonResponse);
            JSONArray recentlyPlayedArrays = paredJsonObject.getJSONArray("items");


            for (int i = 0; i < recentlyPlayedArrays.length(); i++) {

                JSONObject tracks = recentlyPlayedArrays.getJSONObject(i);
                JSONObject track = tracks.getJSONObject("track");

                String nameOfTrack = track.getString("name");

                recentlyPlayedList.add(nameOfTrack);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return recentlyPlayedList;
    }

}
