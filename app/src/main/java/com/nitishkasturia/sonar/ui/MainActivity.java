package com.nitishkasturia.sonar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
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

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationDrawerContainer;
    NavigationView mNavigationDrawer;
    NavigationView mNavigationDrawerBottom;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerContainer = (NavigationView) findViewById(R.id.navigation_drawer_container);
        mNavigationDrawer = (NavigationView) findViewById(R.id.navigation_drawer);
        mNavigationDrawerBottom = (NavigationView) findViewById(R.id.navigation_drawer_bottom);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        //Setup toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationDrawerContainer);
            }
        });

        //Setup navigation drawer
        mNavigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.music_feed:
                        ((TextView) mToolbar.getChildAt(0)).setText(getResources().getString(R.string.music_stream));
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, MusicStreamFragment.newInstance()).commit();
                        menuItem.setChecked(true);
                        break;
                    case R.id.my_tracks:
                        ((TextView) mToolbar.getChildAt(0)).setText(getResources().getString(R.string.my_tracks));
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, MyTracksFragment.newInstance()).commit();
                        menuItem.setChecked(true);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mNavigationDrawerBottom.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

        setupNavigationDrawerHeader();

        ((TextView) mToolbar.getChildAt(0)).setText(getResources().getString(R.string.music_stream));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, MusicStreamFragment.newInstance()).commit();
    }

    private void setupNavigationDrawerHeader() {
        View headerView = View.inflate(this, R.layout.navigation_menu_header, null);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) mToolbar.getChildAt(0)).setText(getResources().getString(R.string.profile));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, ProfileFragment.newInstance()).commit();
                mNavigationDrawer.getMenu().getItem(0).setChecked(false);
                mNavigationDrawer.getMenu().getItem(1).setChecked(false);
                mDrawerLayout.closeDrawers();
            }
        });

        CircleImageView image = (CircleImageView) headerView.findViewById(R.id.profile_image);
        final TextView name = (TextView) headerView.findViewById(R.id.user_full_name);
        final TextView posts = (TextView) headerView.findViewById(R.id.posts);
        final TextView followers = (TextView) headerView.findViewById(R.id.followers);
        final TextView following = (TextView) headerView.findViewById(R.id.following);

        Picasso.with(this).load("https://graph.facebook.com/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture?type=large").into(image);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                try {
                    name.setText((String) jsonObject.get("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        request.executeAsync();

        Ion.with(this).load("GET", "https://microsoft-apiappec9a775993c04bddb23e9d3f9a9a0a65.azurewebsites.net/api/users/" + AccessToken.getCurrentAccessToken().getUserId())
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
                            Toast.makeText(getApplicationContext(), "USER IS NULL", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        request.executeAsync();

        mNavigationDrawer.addHeaderView(headerView);
    }
}