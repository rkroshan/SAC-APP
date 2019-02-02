package com.rajatv.surajv.roshank.sac.Intramurals;

public class IntramuralsGamesModal {

    String Game,Gender;

    public IntramuralsGamesModal(){}

    public IntramuralsGamesModal(String game, String gender) {
        Game = game;
        Gender=gender;
    }

    public String getGame() {
        return Game;
    }

    public void setGame(String game) {
        Game = game;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
