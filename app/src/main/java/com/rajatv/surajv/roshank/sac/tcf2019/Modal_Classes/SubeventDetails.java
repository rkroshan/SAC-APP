package com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes;

public class SubeventDetails {
    private String subeventname, currentplace, currenttime, eventId, endTime, reg_startTime, reg_endTime, registration_type, packages,restricted;

    public SubeventDetails() {
    }

    public SubeventDetails(String subeventname, String currentplace, String currenttime, String eventId, String endTime, String reg_startTime, String reg_endTime, String registration_type, String packages, String restricted) {
        this.subeventname = subeventname;
        this.currentplace = currentplace;
        this.currenttime = currenttime;
        this.eventId = eventId;
        this.endTime = endTime;
        this.reg_startTime = reg_startTime;
        this.reg_endTime = reg_endTime;
        this.registration_type = registration_type;
        this.packages = packages;
        this.restricted=restricted;
    }

    public String getSubeventname() {
        return subeventname;
    }

    public void setSubeventname(String subeventname) {
        this.subeventname = subeventname;
    }

    public String getCurrentplace() {
        return currentplace;
    }

    public void setCurrentplace(String currentplace) {
        this.currentplace = currentplace;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReg_startTime() {
        return reg_startTime;
    }

    public void setReg_startTime(String reg_startTime) {
        this.reg_startTime = reg_startTime;
    }

    public String getReg_endTime() {
        return reg_endTime;
    }

    public void setReg_endTime(String reg_endTime) {
        this.reg_endTime = reg_endTime;
    }

    public String getRegistration_type() {
        return registration_type;
    }

    public void setRegistration_type(String registration_type) {
        this.registration_type = registration_type;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    @Override
    public String toString() {
        return "SubeventDetails{" +
                "subeventname='" + subeventname + '\'' +
                ", currentplace='" + currentplace + '\'' +
                ", currenttime='" + currenttime + '\'' +
                ", eventId='" + eventId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", reg_startTime='" + reg_startTime + '\'' +
                ", reg_endTime='" + reg_endTime + '\'' +
                ", registration_type='" + registration_type + '\'' +
                ", packages='" + packages + '\'' +
                '}';
    }
}