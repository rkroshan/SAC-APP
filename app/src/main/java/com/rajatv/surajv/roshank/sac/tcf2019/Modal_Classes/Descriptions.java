package com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes;

public class Descriptions {
    private String eventTitle;
    private String eventDescription;
    private String eventRules;

    public Descriptions(String eventTitle, String eventDescription, String eventRules) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventRules = eventRules;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventRules() {
        return eventRules;
    }

    public void setEventRules(String eventRules) {
        this.eventRules = eventRules;
    }
}
