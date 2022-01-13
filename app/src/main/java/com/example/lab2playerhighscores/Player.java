package com.example.lab2playerhighscores;

public class Player {
    private int playerID;
    private String playerName;

    public Player() {
    }
    public Player(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
    }
    public void setID(int id) {
        this.playerID = id;
    }
    public int getID() {
        return this.playerID;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getPlayerName() {
        return this.playerName;
    }
}