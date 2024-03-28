package com.ragegame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ragegame.game.screens.GameOver;
import com.ragegame.game.screens.MainMenu;
import com.ragegame.game.utils.Account;

import java.util.UUID;

public class RageGame extends Game {
	public SpriteBatch batch;

	public Account account;

	@Override
	public void create() {
		batch = new SpriteBatch();
		account = new Account();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}

	public void changeScreen(Screen screen) {
		batch = new SpriteBatch();
		this.setScreen(screen);
	}

}