package com.ragegame.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ragegame.game.RageGame;

public class HUD {

    public Stage stage;
    private Viewport viewport;

    private static Integer coins;
    private static Integer medals;

    static Label coinsLabel;
    static Label medalsLabel;

    public HUD(SpriteBatch spriteBatch) {
        coins = 0;
        medals = 0;

        viewport = new FitViewport(RageGame.V_Width, RageGame.V_Height, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        coinsLabel = new Label(String.format("Coins: %01d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(coinsLabel).expandX().padTop(10);
        medalsLabel = new Label(String.format("Medals: %01d", medals), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(medalsLabel).expandX().padTop(10);

        stage.addActor(table);

    }


    public void addCoins(int value){
        if (value > coins) {
            coins += value;
            int length = String.valueOf(coins).length();
            if (length == 1) {
                coinsLabel.setText(String.format("Coins: %01d", coins));
            } else if (length == 2) {
                coinsLabel.setText(String.format("Coins: %02d", coins));
            } else if (length == 3) {
                coinsLabel.setText(String.format("Coins: %03d", coins));
            } else if (length == 4) {
                coinsLabel.setText(String.format("Coins: %04d", coins));
            }

        }
    }

    public void addMedals(int value){
        if (value > medals) {
            medals += value;
            medalsLabel.setText(String.format("Medals: %01d", medals));
        }
    }

    public void dispose() { stage.dispose(); }


}
