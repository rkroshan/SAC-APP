package com.rajatv.surajv.roshank.sac.Blogssacapp;

import java.util.ArrayList;

public class Blogs {
String userImageURI;
    String Content;
    String Publisher;
    String UserUID;

    String BlogUID;
    String Likes;
    ArrayList<String> LikedBy;
    String TimeStamp;
    int Liked;

    public Blogs(){

    }

    public Blogs(int like,String blogUID,String content, String publisher, String userUID, String likes,  String timeStamp, String ImageURI) {
        Liked = like;
        Content = content;
        Publisher = publisher;
        UserUID = userUID;
        Likes = likes;
        TimeStamp = timeStamp;
        BlogUID = blogUID;
        userImageURI=ImageURI;
    }

    public String getBlogUID() {
        return BlogUID;
    }

    public void setBlogUID(String blogUID) {
        BlogUID = blogUID;
    }
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getUserUID() {
        return UserUID;
    }

    public void setUserUID(String userUID) {
        UserUID = userUID;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public ArrayList<String> getLikedBy() {
        return LikedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        LikedBy = likedBy;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public String getUserImageURI() {
        return userImageURI;
    }

    public void setUserImageURI(String userImageURI) {
        this.userImageURI = userImageURI;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public int getLiked() {
        return Liked;
    }

    public void setLiked(int liked) {
        Liked = liked;
    }

    @Override
    public String toString() {
        return "Blogs{" +
                "userImageURI='" + userImageURI + '\'' +
                ", Content='" + Content + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", UserUID='" + UserUID + '\'' +
                ", BlogUID='" + BlogUID + '\'' +
                ", Likes='" + Likes + '\'' +
                ", LikedBy=" + LikedBy +
                ", TimeStamp='" + TimeStamp + '\'' +
                ", Liked=" + Liked +
                '}';
    }
}
