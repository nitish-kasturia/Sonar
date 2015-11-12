package com.nitishkasturia.sonar.data;

import java.util.ArrayList;

/**
 * Created by Nitish on 15-09-05.
 */
public class User {
    String id;
    String name;
    String description;
    String location;
    String artist_name;
    ArrayList<String> following;
    ArrayList<String> followers;
    int track_count;
    ArrayList<String> starred_artists;
    ArrayList<String> liked_tracks;

    public String getId() {
        return id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public int getTrack_count() {
        return track_count;
    }

    public void setTrack_count(int track_count) {
        this.track_count = track_count;
    }

    public ArrayList<String> getStarred_artists() {
        return starred_artists;
    }

    public void setStarred_artists(ArrayList<String> starred_artists) {
        this.starred_artists = starred_artists;
    }

    public ArrayList<String> getLiked_tracks() {
        return liked_tracks;
    }

    public void setLiked_tracks(ArrayList<String> liked_tracks) {
        this.liked_tracks = liked_tracks;
    }
}