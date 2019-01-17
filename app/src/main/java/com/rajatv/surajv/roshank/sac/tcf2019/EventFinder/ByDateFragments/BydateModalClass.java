package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByDateFragments;

public class BydateModalClass {

    String eventString;
    String eventname;

    public BydateModalClass(String event, String EventString) {
        this.eventString = EventString;
        this.eventname = event;
    }
    // GETTER
    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }


    public String getEventString() {
        return eventString;
    }

    public void setEventString(String eventString) {
        this.eventString = eventString;
    }

}
