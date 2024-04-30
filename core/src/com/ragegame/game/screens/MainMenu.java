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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
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

    final RageGame game;
    Texture playButtonActive;
    Texture playButtonInactive;

    TextureRegion acPlay;

    TextureRegion inPlay;
    TextureRegion bg;

    Texture background;

    Table table;
    private ScreenViewport screenViewport;
    private Skin skin;
    private Stage stage;
    private Dialog dialog;

    public MainMenu (RageGame game) {
        this.game = game;
        playButtonActive = new Texture("menu/play_button_active.png");
        playButtonInactive = new Texture("menu/play_button_inactive.png");
        background = new Texture("menu/Title_screen_background.png");

        screenViewport = new ScreenViewport();
        skin = new Skin(Gdx.files.internal("menu/Particle Park UI.json"));//find file
        stage = new Stage(screenViewport, this.game.batch);
        Gdx.input.setInputProcessor(stage);

        dialog = new Dialog("Russian RageGame", skin);
        dialog.getTitleLabel().setAlignment(Align.center);
        Table table = dialog.getContentTable();
        table.pad(10);

        table.row();
        table.defaults().width(150);
        TextButton playButton = new TextButton("Play", skin);
        table.add(playButton);

        table.row();
        TextButton quitButton = new TextButton("Quit", skin);
        table.add(quitButton);
        dialog.show(stage);

        playButton.addListener(new ActorGestureListener() {
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
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.DARK_GRAY);
        screenViewport.apply();
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Game.WIDTH*2, Game.HEIGHT*2);
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
