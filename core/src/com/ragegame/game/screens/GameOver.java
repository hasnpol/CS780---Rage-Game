package com.ragegame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ragegame.game.RageGame;
import com.ragegame.game.utils.Constants.Game;

public class GameOver implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    private int screen_w;
    private int screen_h;

    private Viewport viewport;

    final RageGame game;

    Texture gameOverBanner;
    Texture background;
    BitmapFont scoreFont;

    private ScreenViewport screenViewport;
    private Skin skin;
    private Stage stage;
    private Dialog dialog;

    public GameOver (RageGame game) {
        this.game = game;
        screen_h = Game.HEIGHT;
        screen_w = Game.WIDTH;
        gameOverBanner = new Texture("game_over.png");
        background = new Texture("menu/Title_screen_background.png");

        screenViewport = new ScreenViewport();
        skin = new Skin(Gdx.files.internal("menu/Particle Park UI.json"));//find file
        stage = new Stage(screenViewport, this.game.batch);
        Gdx.input.setInputProcessor(stage);

        dialog = new Dialog("You Suck, Game Over", skin);
        dialog.getTitleLabel().setAlignment(Align.center);
        Table table = dialog.getContentTable();
        table.pad(10);

        table.row();
        table.defaults().width(150);
        TextButton tryAgainButton = new TextButton("Try Again", skin);
        table.add(tryAgainButton);

        table.row();
        TextButton quitButton = new TextButton("Quit", skin);
        table.add(quitButton);
        dialog.show(stage);

        tryAgainButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                game.setScreen(new GameScreen(game));

            }
        });

        quitButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                Gdx.app.exit();

            }
        });
    }

    @Override
    public void show () {}

    @Override
    public void render (float delta) {

        ScreenUtils.clear(Color.DARK_GRAY);
        screenViewport.apply();
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, screen_w*2, screen_h*2);
        //stage.getBatch().draw(gameOverBanner, screen_w / 2 - BANNER_WIDTH / 2, screen_h - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        screenViewport.update(width, height, true);
        dialog.setPosition(Math.round((stage.getWidth() - dialog.getWidth()) / 2),
                Math.round((stage.getHeight() - dialog.getHeight()) / 2));

    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {


    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}