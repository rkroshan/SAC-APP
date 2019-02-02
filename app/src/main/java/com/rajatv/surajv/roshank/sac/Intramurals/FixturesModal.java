package com.rajatv.surajv.roshank.sac.Intramurals;

public class FixturesModal {
    private String man_of_match, result, team1,team2, time_from, time_to, type, venue;

    public FixturesModal(){}

    public FixturesModal(String man_of_match, String result, String team1, String team2, String time_from, String time_to, String type, String venue) {
        this.man_of_match = man_of_match;
        this.result = result;
        this.team1 = team1;
        this.team2 = team2;
        this.time_from = time_from;
        this.time_to = time_to;
        this.type = type;
        this.venue = venue;
    }

    public String getMan_of_match() {
        return man_of_match;
    }

    public void setMan_of_match(String man_of_match) {
        this.man_of_match = man_of_match;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
