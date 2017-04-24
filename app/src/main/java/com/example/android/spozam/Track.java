package com.example.android.spozam;

import java.lang.ref.SoftReference;

/**
 * Created by Nisarg on 4/23/2017.
 */

public class Track {
    private String mTrackName;
    private String mTrackPlayUri;

    public Track(String trackName, String traclPlayUri){
        mTrackName = trackName;
        mTrackPlayUri = traclPlayUri;
    }

    public String getmTrackName() {
        return mTrackName;
    }

    public void setmTrackName(String mTrackName) {
        this.mTrackName = mTrackName;
    }

    public String getmTrackPlayUri() {
        return mTrackPlayUri;
    }

    public void setmTrackPlayUri(String mTrackPlayUri) {
        this.mTrackPlayUri = mTrackPlayUri;
    }
}
