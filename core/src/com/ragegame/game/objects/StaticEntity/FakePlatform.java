package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.utils.Constants;

public class FakePlatform extends StaticEntity {

    Body body;
    MapLayer tileLayer;

    public FakePlatform(Body body, MapLayer tileLayer) {
        super(body, Constants.EntityType.OBSTACLE);
        this.body = body;
        this.tileLayer = tileLayer;
    }

    public void rugPull() {
        this.tileLayer.setVisible(false);
        this.markedForDelete = true;
    }

}
