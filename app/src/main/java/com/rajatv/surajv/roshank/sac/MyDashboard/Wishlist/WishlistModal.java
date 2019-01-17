package com.rajatv.surajv.roshank.sac.MyDashboard.Wishlist;

public class WishlistModal {
    private String subeventname,currentplace,currenttime,eventId;
   public WishlistModal(){}

    public WishlistModal(String subeventname, String currentplace, String currenttime,String eventId) {
        this.subeventname = subeventname;
        this.currentplace = currentplace;
        this.currenttime = currenttime;
        this.eventId=eventId;
    }
    // SETTER


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setSubeventname(String subeventname) {
        this.subeventname = subeventname;
    }

    public void setRegister(String register) {
    }

    public void setCurrentplace(String currentplace) {
        this.currentplace = currentplace;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
    //GETTER


    public String getSubeventname() {
        return subeventname;
    }

    public String getCurrentplace() {
        return currentplace;
    }

    public String getCurrenttime() {
        return currenttime;
    }
}