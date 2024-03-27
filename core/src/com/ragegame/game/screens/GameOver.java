package com.ragegame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.ragegame.game.RageGame;
import com.ragegame.game.utils.Constants.Game;

public class GameOver implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    final RageGame game;

    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOver (RageGame game) {
        this.game = game;

        //Load textures and fonts
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    @Override
    public void show () {}

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(gameOverBanner, Game.WIDTH / 2 - BANNER_WIDTH / 2, Game.HEIGHT - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);
        //System.out.println("Game Over");
        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = Game.WIDTH / 2 - tryAgainLayout.width /2;
        float tryAgainY = Game.HEIGHT / 2 - tryAgainLayout.height / 2;
        float mainMenuX = Game.WIDTH / 2 - mainMenuLayout.width /2;
        float mainMenuY = Game.HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        //Checks if hovering over try again button
        if (Gdx.input.getX() >= tryAgainX && Gdx.input.getX() < tryAgainX + tryAgainLayout.width && Gdx.input.getY() >= tryAgainY - tryAgainLayout.height - 15 && Gdx.input.getY() < tryAgainY)
            tryAgainLayout.setText(scoreFont, "Try Again", Color.YELLOW, 0, Align.left, false);

        //Checks if hovering over main menu button
        if (Gdx.input.getX() >= mainMenuX && Gdx.input.getX() < mainMenuX + mainMenuLayout.width && Gdx.input.getY() >= mainMenuY - mainMenuLayout.height - 15 && Gdx.input.getY() < mainMenuY)
            mainMenuLayout.setText(scoreFont, "Main Menu", Color.YELLOW, 0, Align.left, false);

        //If try again and main menu is being pressed
        if (Gdx.input.isTouched()) {
            //Try again
            if (Gdx.input.getX() > tryAgainX && Gdx.input.getX() < tryAgainX + tryAgainLayout.width && Gdx.input.getY() > tryAgainY - tryAgainLayout.height && Gdx.input.getY() < tryAgainY) {
                game.batch.end();
                game.setScreen(new GameScreen(game));
                return;
            }

            //main menu
            if (Gdx.input.getX() > mainMenuX && Gdx.input.getX() < mainMenuX + mainMenuLayout.width && Gdx.input.getY() > mainMenuY - mainMenuLayout.height && Gdx.input.getY() < mainMenuY) {
                game.batch.end();
                game.setScreen(new MainMenu(game));
                return;
            }
        }

        //Draw buttons
        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY + 60);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY + 140);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}