package com.example.tiktukapp.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;
@IgnoreExtraProperties
public class User {
    public String id;
    public String fullname;
    public String username;
    public String password;
    public String email;
    public String[] follow;
    public String[] follower;
    public Long like;
    public List<Video> videos = new ArrayList<>();

    public User() {
    }

    public User(String id, String fullname, String username, String password, String email, String[] follow, String[] follower, Long like) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.follow = follow;
        this.follower = follower;
        this.like = like;
    }

    public User(String id, String fullname, String username, String password, String email, String[] follow, String[] follower, Long like, List<Video> videos) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.follow = follow;
        this.follower = follower;
        this.like = like;
        this.videos = videos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getFollow() {
        return follow;
    }

    public void setFollow(String[] follow) {
        this.follow = follow;
    }

    public String[] getFollower() {
        return follower;
    }

    public void setFollower(String[] follower) {
        this.follower = follower;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
