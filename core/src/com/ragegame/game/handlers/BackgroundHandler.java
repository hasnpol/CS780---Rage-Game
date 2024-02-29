package com.ragegame.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BackgroundHandler {

    ArrayList<Texture> backgrounds = new ArrayList<>(); // Textures for each layer
    ArrayList<Float> backgroundOffsets = new ArrayList<>(); // Offsets for parallax

    public BackgroundHandler() {
        backgrounds.add(new Texture(Gdx.files.internal("maps/desert/BG.png")));
    }

    public void render(float deltaTime, SpriteBatch batch) {
        float worldHeight = 18 * 32;
        float worldWidth = 128 * 32;

        backgroundOffsets.clear();

        backgroundOffsets.add(deltaTime * 1);

        for (int i = 0; i < backgroundOffsets.size(); i++) {
            if (backgroundOffsets.get(i) > worldHeight) {
                backgroundOffsets.set(i, 0f);
            }
            batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i), worldWidth/32f, worldHeight/32f);
            batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i) + (worldWidth/32f), worldWidth/32f, worldHeight/32f);
        }

    }

    public void dispose() {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).dispose();
        }
    }



}
