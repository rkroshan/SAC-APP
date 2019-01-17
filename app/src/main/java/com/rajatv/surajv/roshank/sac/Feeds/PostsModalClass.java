package com.rajatv.surajv.roshank.sac.Feeds;

import java.util.ArrayList;

public class PostsModalClass {

    private int TYPE;
    private String POSTUID;
    private String POSTS_CONTENT;
    private String POSTS_LIKES;
    private String POSTS_PHOTOURL;
    private String POSTS_TIMESTAMP;
    private String POSTS_SUBEVENTNAME;
    private String POSTS_EVENTNAME;
    private String POSTS_PUBLISHER_NAME;
    private String POSTS_PUBLISHER_USERIMAGEURL;
    private String POSTS_PUBLISHER_USERUID;
    private ArrayList<String> DOWNLOADURIS;
    private int DOWNLOADITEM;
    private String TOTALLIKESCOUNT;
    private String TODAYLIKESCOUNT;
    private int LIKEDBYUSER;

    public PostsModalClass(int TYPE, String POSTUID, String POSTS_CONTENT, String POSTS_LIKES, String POSTS_PHOTOURL, String POSTS_TIMESTAMP, String POSTS_SUBEVENTNAME, String POSTS_EVENTNAME, String POSTS_PUBLISHER_NAME, String POSTS_PUBLISHER_USERIMAGEURL, String POSTS_PUBLISHER_USERUID, ArrayList<String> DOWNLOADURIS, int DOWNLOADITEM, String TOTALLIKESCOUNT, String TODAYLIKESCOUNT, int LIKEDBYUSER) {
        this.TYPE = TYPE;
        this.POSTUID = POSTUID;
        this.POSTS_CONTENT = POSTS_CONTENT;
        this.POSTS_LIKES = POSTS_LIKES;
        this.POSTS_PHOTOURL = POSTS_PHOTOURL;
        this.POSTS_TIMESTAMP = POSTS_TIMESTAMP;
        this.POSTS_SUBEVENTNAME = POSTS_SUBEVENTNAME;
        this.POSTS_EVENTNAME = POSTS_EVENTNAME;
        this.POSTS_PUBLISHER_NAME = POSTS_PUBLISHER_NAME;
        this.POSTS_PUBLISHER_USERIMAGEURL = POSTS_PUBLISHER_USERIMAGEURL;
        this.POSTS_PUBLISHER_USERUID = POSTS_PUBLISHER_USERUID;
        this.DOWNLOADURIS = DOWNLOADURIS;
        this.DOWNLOADITEM = DOWNLOADITEM;
        this.TOTALLIKESCOUNT = TOTALLIKESCOUNT;
        this.TODAYLIKESCOUNT = TODAYLIKESCOUNT;
        this.LIKEDBYUSER = LIKEDBYUSER;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getPOSTUID() {
        return POSTUID;
    }

    public void setPOSTUID(String POSTUID) {
        this.POSTUID = POSTUID;
    }

    public String getPOSTS_CONTENT() {
        return POSTS_CONTENT;
    }

    public void setPOSTS_CONTENT(String POSTS_CONTENT) {
        this.POSTS_CONTENT = POSTS_CONTENT;
    }

    public String getPOSTS_LIKES() {
        return POSTS_LIKES;
    }

    public void setPOSTS_LIKES(String POSTS_LIKES) {
        this.POSTS_LIKES = POSTS_LIKES;
    }

    public String getPOSTS_PHOTOURL() {
        return POSTS_PHOTOURL;
    }

    public void setPOSTS_PHOTOURL(String POSTS_PHOTOURL) {
        this.POSTS_PHOTOURL = POSTS_PHOTOURL;
    }

    public String getPOSTS_TIMESTAMP() {
        return POSTS_TIMESTAMP;
    }

    public void setPOSTS_TIMESTAMP(String POSTS_TIMESTAMP) {
        this.POSTS_TIMESTAMP = POSTS_TIMESTAMP;
    }

    public String getPOSTS_SUBEVENTNAME() {
        return POSTS_SUBEVENTNAME;
    }

    public void setPOSTS_SUBEVENTNAME(String POSTS_SUBEVENTNAME) {
        this.POSTS_SUBEVENTNAME = POSTS_SUBEVENTNAME;
    }

    public String getPOSTS_EVENTNAME() {
        return POSTS_EVENTNAME;
    }

    public void setPOSTS_EVENTNAME(String POSTS_EVENTNAME) {
        this.POSTS_EVENTNAME = POSTS_EVENTNAME;
    }

    public String getPOSTS_PUBLISHER_NAME() {
        return POSTS_PUBLISHER_NAME;
    }

    public void setPOSTS_PUBLISHER_NAME(String POSTS_PUBLISHER_NAME) {
        this.POSTS_PUBLISHER_NAME = POSTS_PUBLISHER_NAME;
    }

    public String getPOSTS_PUBLISHER_USERIMAGEURL() {
        return POSTS_PUBLISHER_USERIMAGEURL;
    }

    public void setPOSTS_PUBLISHER_USERIMAGEURL(String POSTS_PUBLISHER_USERIMAGEURL) {
        this.POSTS_PUBLISHER_USERIMAGEURL = POSTS_PUBLISHER_USERIMAGEURL;
    }

    public String getPOSTS_PUBLISHER_USERUID() {
        return POSTS_PUBLISHER_USERUID;
    }

    public void setPOSTS_PUBLISHER_USERUID(String POSTS_PUBLISHER_USERUID) {
        this.POSTS_PUBLISHER_USERUID = POSTS_PUBLISHER_USERUID;
    }

    public ArrayList<String> getDOWNLOADURIS() {
        return DOWNLOADURIS;
    }

    public void setDOWNLOADURIS(ArrayList<String> DOWNLOADURIS) {
        this.DOWNLOADURIS = DOWNLOADURIS;
    }

    public int getDOWNLOADITEM() {
        return DOWNLOADITEM;
    }

    public void setDOWNLOADITEM(int DOWNLOADITEM) {
        this.DOWNLOADITEM = DOWNLOADITEM;
    }

    public String getTOTALLIKESCOUNT() {
        return TOTALLIKESCOUNT;
    }

    public void setTOTALLIKESCOUNT(String TOTALLIKESCOUNT) {
        this.TOTALLIKESCOUNT = TOTALLIKESCOUNT;
    }

    public String getTODAYLIKESCOUNT() {
        return TODAYLIKESCOUNT;
    }

    public void setTODAYLIKESCOUNT(String TODAYLIKESCOUNT) {
        this.TODAYLIKESCOUNT = TODAYLIKESCOUNT;
    }

    public int getLIKEDBYUSER() {
        return LIKEDBYUSER;
    }

    public void setLIKEDBYUSER(int LIKEDBYUSER) {
        this.LIKEDBYUSER = LIKEDBYUSER;
    }

    @Override
    public String toString() {
        return "PostsModalClass{" +
                "TYPE=" + TYPE +
                ", POSTUID='" + POSTUID + '\'' +
                ", POSTS_CONTENT='" + POSTS_CONTENT + '\'' +
                ", POSTS_LIKES='" + POSTS_LIKES + '\'' +
                ", POSTS_PHOTOURL='" + POSTS_PHOTOURL + '\'' +
                ", POSTS_TIMESTAMP='" + POSTS_TIMESTAMP + '\'' +
                ", POSTS_SUBEVENTNAME='" + POSTS_SUBEVENTNAME + '\'' +
                ", POSTS_EVENTNAME='" + POSTS_EVENTNAME + '\'' +
                ", POSTS_PUBLISHER_NAME='" + POSTS_PUBLISHER_NAME + '\'' +
                ", POSTS_PUBLISHER_USERIMAGEURL='" + POSTS_PUBLISHER_USERIMAGEURL + '\'' +
                ", POSTS_PUBLISHER_USERUID='" + POSTS_PUBLISHER_USERUID + '\'' +
                ", DOWNLOADURIS=" + DOWNLOADURIS +
                ", DOWNLOADITEM=" + DOWNLOADITEM +
                ", TOTALLIKESCOUNT='" + TOTALLIKESCOUNT + '\'' +
                ", TODAYLIKESCOUNT='" + TODAYLIKESCOUNT + '\'' +
                ", LIKEDBYUSER=" + LIKEDBYUSER +
                '}';
    }
}
