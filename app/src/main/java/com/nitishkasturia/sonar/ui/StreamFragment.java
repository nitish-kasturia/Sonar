package com.nitishkasturia.sonar.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.adapters.MusicStreamAdapter;
import com.nitishkasturia.sonar.data.Track;
import com.nitishkasturia.sonar.ui.views.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitish on 15-09-05.
 */
public class StreamFragment extends Fragment {

    private static final String STREAM_TYPE = "TYPE";

    public static final int TYPE_HOT = 0;
    public static final int TYPE_NEW = 1;
    public static final int TYPE_FOLLOWING = 2;

    int mStreamType = 1;

    List<Track> mTrackList = new ArrayList<>();

    public static StreamFragment newInstance(int streamType) {
        StreamFragment fragment = new StreamFragment();
        Bundle args = new Bundle();

        args.putInt(STREAM_TYPE, streamType);
        fragment.setArguments(args);
        return fragment;
    }

    public StreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStreamType = getArguments().getInt(STREAM_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream, container, false);

        final ListView list = (ListView) view.findViewById(R.id.song_list);

        if (mStreamType == TYPE_HOT) {
            Ion.with(getActivity()).load("GET", "https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/feed/hot?city=toronto&state=ontario")
                    .as(new TypeToken<List<Track>>() {
                    })
                    .setCallback(new FutureCallback<List<Track>>() {
                        @Override
                        public void onCompleted(Exception e, List<Track> tracks) {
                            if (tracks != null) {
                                mTrackList = tracks;
                                list.setAdapter(new MusicStreamAdapter(getContext(), mTrackList));
                            }
                        }
                    });
        } else if (mStreamType == TYPE_NEW) {
            Ion.with(getActivity()).load("GET", "https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/feed/new?city=toronto&state=ontario")
                    .as(new TypeToken<List<Track>>() {
                    })
                    .setCallback(new FutureCallback<List<Track>>() {
                        @Override
                        public void onCompleted(Exception e, List<Track> tracks) {
                            if (tracks != null) {
                                mTrackList = tracks;
                                list.setAdapter(new MusicStreamAdapter(getContext(), mTrackList));
                            }
                        }
                    });
        }

        list.setAdapter(new MusicStreamAdapter(getContext(), mTrackList));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String source = ((TextView) parent.findViewById(R.id.track_source)).getText().toString();
                Toast.makeText(getActivity(), source, Toast.LENGTH_LONG).show();
                MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(source));
                mediaPlayer.start();
            }
        });

        return view;
    }
}