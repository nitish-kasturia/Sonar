package com.nitishkasturia.sonar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.melnykov.fab.FloatingActionButton;
import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.adapters.MusicStreamAdapter;
import com.nitishkasturia.sonar.data.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitish on 15-09-05.
 */
public class MyTracksFragment extends Fragment {

    List<Track> mTrackList = new ArrayList<>();
    ListView mTrackListView = null;
    FloatingActionButton mFab = null;

    public static MyTracksFragment newInstance() {
        return new MyTracksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tracks, container, false);

        mTrackListView = (ListView) view.findViewById(R.id.track_list);
        mFab = (FloatingActionButton) view.findViewById(R.id.new_track);

        Ion.with(getActivity()).load("https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/users/" + AccessToken.getCurrentAccessToken().getUserId() + "/tracks")
                .as(new TypeToken<List<Track>>() {
                })
                .setCallback(new FutureCallback<List<Track>>() {
                    @Override
                    public void onCompleted(Exception e, List<Track> tracks) {
                        if (tracks != null) {
                            mTrackList = tracks;
                            mTrackListView.setAdapter(new MusicStreamAdapter(getContext(), mTrackList));
                        }
                    }
                });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrackUploadActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}