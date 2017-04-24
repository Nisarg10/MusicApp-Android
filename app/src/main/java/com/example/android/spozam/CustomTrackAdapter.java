package com.example.android.spozam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nisarg on 4/23/2017.
 */

public class CustomTrackAdapter extends RecyclerView.Adapter<CustomTrackAdapter.CustomTrackHolder> {

    private List<Track> mTrackList;
    public TextView mTrackName;

    public CustomTrackAdapter(Context context, List<Track> trackList) {
        mTrackList = trackList;
    }

    class CustomTrackHolder extends RecyclerView.ViewHolder {
        private TextView trackName;
        private TextView trackArtist;

        public CustomTrackHolder(View view) {
            super(view);
            trackName = (TextView) view.findViewById(R.id.tv_track_name);
            trackArtist = (TextView) view.findViewById(R.id.tv_artist_name);
        }
    }

    @Override
    public CustomTrackAdapter.CustomTrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);

        return new CustomTrackHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomTrackAdapter.CustomTrackHolder holder, int position) {
        Track track = mTrackList.get(position);

        holder.trackName.setText(track.getmTrackName());
        holder.trackArtist.setText(track.getmTrackPlayUri());
    }

    @Override
    public int getItemCount() {
        return (mTrackList != null) ? mTrackList.size() : 0;
    }
}
