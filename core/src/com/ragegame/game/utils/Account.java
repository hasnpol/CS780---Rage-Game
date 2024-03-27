package com.ragegame.game.utils;

public class Account {

    private int currency;

    private String user;
    private int id;

    public Account(String user, int id) {
        this.currency = 0;
        this.id = id;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public void setUser(String name) {
        this.user = name;
    }

    public int getId() {
        return id;
    }

    public int getCurrency() {
        return currency;
    }

    public String getUser() {
        return user;
    }
}
