package com.nitishkasturia.sonar.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nitishkasturia.sonar.R;
import com.nitishkasturia.sonar.data.User;
import com.nitishkasturia.sonar.ui.views.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        CircleImageView profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        final TextView fullName = (TextView) view.findViewById(R.id.user_full_name);
        final TextView posts = (TextView) view.findViewById(R.id.posts);
        final TextView followers = (TextView) view.findViewById(R.id.followers);
        final TextView following = (TextView) view.findViewById(R.id.following);

        Picasso.with(getContext()).load("https://graph.facebook.com/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture?type=large").into(profileImage);

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                try {
                    fullName.setText((String) jsonObject.get("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Ion.with(getActivity()).load("GET", "https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/users/" + AccessToken.getCurrentAccessToken().getUserId())
                .as(new TypeToken<User>() {
                })
                .setCallback(new FutureCallback<User>() {
                    @Override
                    public void onCompleted(Exception e, User user) {
                        if (user != null) {
                            posts.setText(user.getTrack_count() + "\nPosts");
                            followers.setText(user.getFollowers().size() + "\nFollowers");
                            following.setText(user.getFollowing().size() + "\nFollowing");
                        } else {
                            Toast.makeText(getActivity(), "USER IS NULL", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        request.executeAsync();

        return view;
    }
}