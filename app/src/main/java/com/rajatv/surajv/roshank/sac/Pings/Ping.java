package com.rajatv.surajv.roshank.sac.Pings;

public class Ping {
  String profilePicUrl, name,userUid;

  public Ping()
  {}

    public Ping(String profilePicUrl, String name, String userUid) {
        this.profilePicUrl = profilePicUrl;
        this.name = name;
        this.userUid = userUid;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
