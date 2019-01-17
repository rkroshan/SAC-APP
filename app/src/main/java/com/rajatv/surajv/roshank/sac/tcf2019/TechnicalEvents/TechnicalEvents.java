package com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents;

public class TechnicalEvents {
    String eventname, eventnameabout;

    public TechnicalEvents(String eventname, String eventnameabout) {
        this.eventname = eventname;
        this.eventnameabout = eventnameabout;
    }
    // SETTER

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setEventnameabout(String eventnameabout) {
        this.eventnameabout = eventnameabout;
    }
    //GETTER


    public String getEventname() {
        return eventname;
    }

    public String getEventnameabout() {
        return eventnameabout;
    }
}
