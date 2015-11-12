package com.nitishkasturia.sonar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.data.Track;
import com.nitishkasturia.sonar.data.User;
import com.nitishkasturia.sonar.ui.views.TextView;

import java.util.List;

/**
 * Created by Nitish on 15-09-05.
 */
public class MusicStreamAdapter extends ArrayAdapter<Track> {

    private Context mContext;
    private List<Track> mObjects;

    public MusicStreamAdapter(Context context, List<Track> objects) {
        super(context, R.layout.list_item_song, objects);
        this.mContext = context;
        this.mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.list_item_song, null);

        TextView songName = (TextView) rowView.findViewById(R.id.song_name);
        TextView trackSource = (TextView) rowView.findViewById(R.id.track_source);
        TextView likes = (TextView) rowView.findViewById(R.id.likes);
        final TextView artistName = (TextView) rowView.findViewById(R.id.artist_name);

        songName.setText(mObjects.get(position).getName());
        likes.setText(mObjects.get(position).getLikes() + "");

        Ion.with(mContext).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/users/" + mObjects.get(position).getId())
                .as(new TypeToken<User>() {
                })
                .setCallback(new FutureCallback<User>() {
                    @Override
                    public void onCompleted(Exception e, User result) {
                        artistName.setText(result.getName());
                    }
                });
        trackSource.setText(mObjects.get(position).getSource());
        return rowView;
    }
}