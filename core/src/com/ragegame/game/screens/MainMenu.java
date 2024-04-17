package com.ragegame.game.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ragegame.game.RageGame;
import com.ragegame.game.utils.Constants.Game;

public class MainMenu implements Screen {

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_Y = 230;

    private int screen_w;
    private int screen_h;

    final RageGame game;
    Texture playButtonActive;
    Texture playButtonInactive;

    TextureRegion acPlay;

    TextureRegion inPlay;
    TextureRegion bg;

    Texture background;

    Table table;

    public Stage stage;
    private final Viewport viewport;

    public MainMenu (RageGame game) {
        this.game = game;
        playButtonActive = new Texture("menu/play_button_active.png");
        playButtonInactive = new Texture("menu/play_button_inactive.png");
        background = new Texture("menu/Title_screen_background.png");
        viewport = new FillViewport((float) Game.WIDTH /2, (float) Game.HEIGHT /2);
        screen_h = Game.HEIGHT;
        screen_w = Game.WIDTH;

        final MainMenu mainMenuScreen = this;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                //Play game button
                int x = screen_w / 2 - PLAY_BUTTON_WIDTH / 2;
                if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && screen_h - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && screen_h - Gdx.input.getY() > PLAY_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    game.setScreen(new GameScreen(game));
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        //game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        //System.out.println("Main 1");

        game.batch.draw(background, 0, 0);

        int x = screen_w / 2 - PLAY_BUTTON_WIDTH / 2;//350
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && screen_h - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && screen_h - Gdx.input.getY() > PLAY_BUTTON_Y) {
            game.batch.draw(playButtonActive, 350, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playButtonInactive, 350, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        screen_w = width;
        screen_h = height;
        viewport.update(width, height);
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

    }
}
