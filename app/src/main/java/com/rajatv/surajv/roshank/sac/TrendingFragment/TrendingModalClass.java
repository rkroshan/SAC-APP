package com.rajatv.surajv.roshank.sac.TrendingFragment;

public class TrendingModalClass {
    private Integer oldRank, newRank;
    private String username;
    private String profile_pic;
    private Integer todaylikecount;
    private Integer totallikecount;
    private String userUID;

    public TrendingModalClass( String username, String profile_pic, Integer todaylikecount, Integer totallikecount, String userUID,Integer oldRank, Integer newRank) {
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.username = username;
        this.profile_pic = profile_pic;
        this.todaylikecount = todaylikecount;
        this.totallikecount = totallikecount;
        this.userUID = userUID;
    }

    public TrendingModalClass() {
    }

    public Integer getOldRank() {
        return oldRank;
    }

    public void setOldRank(Integer oldRank) {
        this.oldRank = oldRank;
    }

    public Integer getNewRank() {
        return newRank;
    }

    public void setNewRank(Integer newRank) {
        this.newRank = newRank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Integer getTodaylikecount() {
        return todaylikecount;
    }

    public void setTodaylikecount(Integer todaylikecount) {
        this.todaylikecount = todaylikecount;
    }

    public Integer getTotallikecount() {
        return totallikecount;
    }

    public void setTotallikecount(Integer totallikecount) {
        this.totallikecount = totallikecount;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}