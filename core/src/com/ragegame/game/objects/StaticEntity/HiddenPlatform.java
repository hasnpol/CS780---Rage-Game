package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.physics.box2d.Body;

public class HiddenPlatform extends Platform {

    MapLayer tileLayer;

    public HiddenPlatform(Body body, float x, float y, MapLayer tileLayer) {
        super(body, x, y);
        this.tileLayer = tileLayer;
        hide();
    }

    public void reveal() {
        this.tileLayer.setVisible(true);
    }

    public void hide() {
        this.tileLayer.setVisible(false);
    }
}
