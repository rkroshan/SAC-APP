package com.rajatv.surajv.roshank.sac.Feeds.Result;

import java.util.ArrayList;

public class Results {

    private String resultUploaderImage;
    private String resultUploaderName;
    private String resultUploadTime;
    private String resultMessage;
    private String resultLikes;
    private String subEventName;
    private String eventName;
    private int downloadItem;
    private String ResultUID;
    private ArrayList<String> downloadList;
    private String userUID;
    private int liked;

    public ArrayList<String> getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(ArrayList<String> downloadList) {
        this.downloadList = downloadList;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Results(int like,String resultUID, String resultUploaderImage, String resultUploaderName, String resultUploadTime, String resultMessage, String resultLikes, String subEventName, String eventName, int downloadItem, ArrayList<String> downloadList, String userUID) {
        this.ResultUID = resultUID;
        this.resultUploaderImage = resultUploaderImage;
        this.resultUploaderName = resultUploaderName;
        this.resultUploadTime = resultUploadTime;
        this.resultMessage = resultMessage;
        this.resultLikes = resultLikes;
        this.subEventName = subEventName;
        this.eventName = eventName;
        this.downloadItem = downloadItem;
        this.downloadList = downloadList;
        this.userUID = userUID;
        this.liked = like;
    }


    public int getDownloadItem() {
        return downloadItem;
    }

    public void setDownloadItem(int downloadItem) {
        this.downloadItem = downloadItem;
    }

    public Results() {
    }

    public String getResultUploaderImage() {
        return resultUploaderImage;
    }

    public String getResultUploaderName() {
        return resultUploaderName;
    }

    public String getResultUploadTime() {
        return resultUploadTime;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultLikes() {
        return resultLikes;
    }

    public String getSubEventName() {
        return subEventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setResultUploaderImage(String resultUploaderImage) {
        this.resultUploaderImage = resultUploaderImage;
    }

    public void setResultUploaderName(String resultUploaderName) {
        this.resultUploaderName = resultUploaderName;
    }

    public void setResultUploadTime(String resultUploadTime) {
        this.resultUploadTime = resultUploadTime;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setResultLikes(String resultLikes) {
        this.resultLikes = resultLikes;
    }

    public void setSubEventName(String subEventName) {
        this.subEventName = subEventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getResultUID() {
        return ResultUID;
    }

    public void setResultUID(String resultUID) {
        ResultUID = resultUID;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Results{" +
                "resultUploaderImage='" + resultUploaderImage + '\'' +
                ", resultUploaderName='" + resultUploaderName + '\'' +
                ", resultUploadTime='" + resultUploadTime + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultLikes='" + resultLikes + '\'' +
                ", subEventName='" + subEventName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", downloadItem=" + downloadItem +
                ", ResultUID='" + ResultUID + '\'' +
                ", downloadList=" + downloadList +
                ", userUID='" + userUID + '\'' +
                ", liked=" + liked +
                '}';
    }
}

