package com.ragegame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ragegame.game.screens.MainMenu;

import java.util.UUID;

public class RageGame extends Game {

	public static final int HEIGHT = 800;
	public static final int WIDTH = 1000;
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));

	}

	@Override
	public void render() {

		super.render();


	}

	@Override
	public void dispose () {
	}

}