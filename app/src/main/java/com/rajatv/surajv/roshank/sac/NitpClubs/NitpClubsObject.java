package com.rajatv.surajv.roshank.sac.NitpClubs;

/**
 * Created by HP on 17-12-2017.
 */

class NitpClubsObject {
    private String clubName;

    public NitpClubsObject(String clubName, int imageId) {
        this.clubName = clubName;
        this.imageId = imageId;
    }
    public NitpClubsObject()
    {
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;
}
