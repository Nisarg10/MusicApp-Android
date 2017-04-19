package com.example.android.spozam;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.net.Uri.withAppendedPath;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class HomeActivity extends AppCompatActivity {

    private final static String LOG_TAG = HomeActivity.class.getSimpleName();

    private TextView mPlaylist;
    private TextView mAlbums;
    private TextView mArtists;
    private TextView mSongs;

    private static String accessToken;
    private final static String BASE_URI = "https://api.spotify.com/";
    private final static String VERSION = "v1/";
    private final static String CONTENT_URI = BASE_URI + VERSION + "me";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        accessToken = intent.getExtras().getString("access-token");
        Log.d("HomeActivity", "Access token is: " + accessToken);

        final Uri baseUriRecently = Uri.parse(CONTENT_URI);
        String recentlyPlayed = "player/recently-played";
        Uri updatedRecent = baseUriRecently.withAppendedPath(baseUriRecently, recentlyPlayed);
        new PlayListAsyncTask().execute(updatedRecent.toString(), accessToken);

        mPlaylist = (TextView) findViewById(R.id.tv_playlist);
        mPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "Clicked item:" + mPlaylist.getText().toString().toLowerCase());
                final Uri baseUri = Uri.parse(CONTENT_URI);
                String uriToAppend = mPlaylist.getText().toString().toLowerCase();
                Uri updatedUri = baseUri.withAppendedPath(baseUri, uriToAppend);

                Log.d(LOG_TAG, "URL is " + updatedUri.toString());
                new PlayListAsyncTask().execute(updatedUri.toString(), accessToken);
            }
        });

        mAlbums = (TextView) findViewById(R.id.tv_albums);
        mAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "Clicked item:" + mAlbums.getText().toString().toLowerCase());
            }
        });

        mArtists = (TextView) findViewById(R.id.tv_artists);
        mArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "Clicked item:" + mArtists.getText().toString().toLowerCase());
            }
        });

        mSongs = (TextView) findViewById(R.id.tv_songs);
        mSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeActivity", "Clicked item:" + mSongs.getText().toString().toLowerCase());
                //baseUri.withAppendedPath(baseUri, "tracks");
            }
        });
    }

    private void updateUI(List<String> recentlyPlayedList){
        ArrayAdapter recenlyPlayedAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recentlyPlayedList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(recenlyPlayedAdapter);
    }

    private class PlayListAsyncTask extends AsyncTask<String ,Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {

            List<String> recentlyPlayedList = QueryUtils.fetchData(params[0], params[1]);

            return recentlyPlayedList;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            updateUI(strings);
        }
    }

}
