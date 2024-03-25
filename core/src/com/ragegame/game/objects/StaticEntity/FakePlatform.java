package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.utils.Constants;

public class FakePlatform extends Platform {

    Body body;
    MapLayer tileLayer;

    public FakePlatform(Body body, float x, float y, MapLayer tileLayer) {
        super(body, x, y);
        this.body = body;
        this.tileLayer = tileLayer;
    }

    public void rugPull() {
        this.tileLayer.setVisible(false);
        this.markedForDelete = true;
    }

}
