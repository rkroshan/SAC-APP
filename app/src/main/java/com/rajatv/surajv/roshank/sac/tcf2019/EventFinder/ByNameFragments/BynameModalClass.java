package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByNameFragments;

public class BynameModalClass {
    private String subeventname, currentplace, currenttime,useruid;

    public BynameModalClass() {
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public BynameModalClass(String subeventname, String currentplace, String currenttime, String useruid) {

        this.subeventname = subeventname;
        this.currentplace = currentplace;
        this.currenttime = currenttime;
        this.useruid = useruid;
    }

    // SETTER


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
