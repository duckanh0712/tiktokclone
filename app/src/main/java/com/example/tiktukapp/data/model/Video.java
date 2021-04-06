package com.example.tiktukapp.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.time.LocalDateTime;
@IgnoreExtraProperties
public class Video {
    public String title;
    public String url;
    public Long like;
    //public String category;
    //public LocalDateTime cratedAt;
    public String uploadBy;

    public Video() {
    }

//    public Video(String title, String url, Long like, LocalDateTime cratedAt, String uploadBy) {
//        this.title = title;
//        this.url = url;
//        this.like = like;
//        //this.cratedAt = cratedAt;
//        this.uploadBy = uploadBy;
//    }

    public Video(String title, String url, Long like, String upload_by) {
        this.title = title;
        this.url = url;
        this.like = like;
        this.uploadBy = upload_by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }


//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }

//    public LocalDateTime getCratedAt() {
//        return cratedAt;
//    }
//
//    public void setCratedAt(LocalDateTime cratedAt) {
//        this.cratedAt = cratedAt;
//    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }
}
