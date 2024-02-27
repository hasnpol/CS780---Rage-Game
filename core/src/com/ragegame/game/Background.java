package com.ragegame.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {
    Texture texture;
    float factor;
    boolean wrapHorizontally;
    boolean wrapVertically;
    Camera camera;

    public Background(Texture texture, float factor, boolean wrapVertically, boolean wrapHorizontally) {

        this.texture = texture;
        this.factor = factor;
        this.wrapVertically = wrapVertically;
        this.wrapHorizontally = wrapHorizontally;

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void render(SpriteBatch batch) {
        int x = (int) (camera.position.x * factor);
        int y = (int) (camera.position.y * factor);
        TextureRegion region = new TextureRegion(texture);
        region.setRegionX(x % texture.getWidth());
        region.setRegionY(y % texture.getHeight());
        region.setRegionHeight(wrapHorizontally ? (int) camera.viewportWidth : texture.getWidth());
        region.setRegionHeight(wrapVertically ? (int) camera.viewportHeight : texture.getHeight());
        batch.draw(region, camera.position.x - camera.viewportWidth/2, camera.position.y - camera.viewportHeight/2);
    }





}
