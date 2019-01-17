package com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments;

public class CulturalEvents {
     String eventname,about,id;

    public CulturalEvents(String eventname,String about,String id) {
        this.eventname = eventname;
        this.about=about;
        this.id=id;
    }
    // GETTER
    public String getEventname() {
        return eventname;
    }

    public String getAbout() {
        return about;
    }

    public void setId(String id) {
        this.id = id;
    }

    //SETTER
    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }
}
