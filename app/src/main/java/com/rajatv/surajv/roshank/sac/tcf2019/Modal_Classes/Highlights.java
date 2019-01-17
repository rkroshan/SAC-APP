package com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes;

public class Highlights {

    private String eventName, dateTime, going, othersGoing, imageUri, key;
    private int liked;

    public Highlights(int like,String eventName, String dateTime, String going, String othersGoing, String imageUri, String key) {
        this.liked = like;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.going = going;
        this.othersGoing = othersGoing;
        this.imageUri = imageUri;
        this.key = key;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getOthersGoing() {
        return othersGoing;
    }

    public void setOthersGoing(String othersGoing) {
        this.othersGoing = othersGoing;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
