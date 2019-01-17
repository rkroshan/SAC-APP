package com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes;

public class TodayTomorrowModalClass {

    String subeventname;
    String eventname;
    String time;
    String location;
    String round;

    public TodayTomorrowModalClass(String subeventname, String eventname, String time, String location, String round) {

        this.subeventname = subeventname;
        this.eventname = eventname;
        this.time = time;
        this.location = location;
        this.round = round;
    }


    public String getSubeventname() {
        return subeventname;
    }

    public void setSubeventname(String subeventname) {
        this.subeventname = subeventname;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTime() {
        return time;
    }

    public void setTiming(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

}

