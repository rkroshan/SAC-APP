package com.rajatv.surajv.roshank.sac.Feeds.Notice;

import java.util.ArrayList;

public class Notice {

    private String Content, EventName, Heading;
    private String Likes;
    private String UserImage;


    private String NoticeUID;
    private String PostedBy;
    private String SubEventName;
    private String TimeStamp, LikesBY, UserUID;
    private ArrayList<String> downloadList;
    private int downloadItem;
    private int liked;


    public ArrayList<String> getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(ArrayList<String> downloadList) {
        this.downloadList = downloadList;
    }

    public int getDownloadItem() {
        return downloadItem;
    }

    public void setDownloadItem(int downloadItem) {
        this.downloadItem = downloadItem;
    }

    public Notice(int like, String noticeUID, String content, String eventName, String heading, String likes, String userImage, String postedBy, String subEventName, String timeStamp, String likesBY, String userUID, ArrayList<String> downloadList, int downloadItem) {
        NoticeUID = noticeUID;
        Content = content;
        EventName = eventName;
        Heading = heading;
        Likes = likes;
        UserImage = userImage;
        PostedBy = postedBy;
        SubEventName = subEventName;
        TimeStamp = timeStamp;
        LikesBY = likesBY;
        UserUID = userUID;
        this.downloadList = downloadList;
        this.downloadItem = downloadItem;
        this.liked = like;


    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }


    public String getPostedBy() {
        return PostedBy;
    }

    public void setPostedBy(String postedBy) {
        PostedBy = postedBy;
    }

    public String getSubEventName() {
        return SubEventName;
    }

    public void setSubEventName(String subEventName) {
        SubEventName = subEventName;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getLikesBY() {
        return LikesBY;
    }

    public void setLikesBY(String likesBY) {
        LikesBY = likesBY;
    }

    public String getUserUID() {
        return UserUID;
    }

    public void setUserUID(String userUID) {
        UserUID = userUID;
    }
    public String getNoticeUID() {
        return NoticeUID;
    }

    public void setNoticeUID(String noticeUID) {
        NoticeUID = noticeUID;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}