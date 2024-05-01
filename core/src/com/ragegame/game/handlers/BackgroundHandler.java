package com.ragegame.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class BackgroundHandler {

    ArrayList<Texture> backgrounds = new ArrayList<>(); // Textures for each layer
    ArrayList<Float> backgroundOffsets = new ArrayList<>(); // Offsets for parallax

    public BackgroundHandler() {
        backgrounds.add(new Texture(Gdx.files.internal("maps/map/background/grassy_plains_by_theodenn.jpg")));
    }

    public void render(float deltaTime, SpriteBatch batch, float worldWidth, float worldHeight, float PPM) {
        worldHeight *= PPM;
        worldWidth *= PPM;

        backgroundOffsets.clear();
        backgroundOffsets.add(deltaTime * 1);

        for (int i = 0; i < backgroundOffsets.size(); i++) {
            if (backgroundOffsets.get(i) > worldHeight) {
                backgroundOffsets.set(i, 0f);
            }
            batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i), worldWidth/PPM, worldHeight/PPM);
            batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i) + (worldWidth/PPM), worldWidth/PPM, worldHeight/PPM);
        }

    }

    public void dispose() {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).dispose();
        }
    }

}
