package com.ragegame.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Account {

    private final Preferences account;

    public Account() {
        account = Gdx.app.getPreferences("RageGame Data");
    }

    public void setCurrency(int currency) {
        int acc = getCurrency() + currency;
        String cur = Integer.toString(acc);
        this.account.putString("accountBalance", cur);
    }

    public int getCurrency() {
        return this.account.getInteger("accountBalance");
    }

    public void flush() {//Saves new data
        this.account.flush();
    }
}
