package com.ragegame.game.objects.StaticEntity;

import static com.ragegame.game.utils.Constants.EntityType.OBSTACLE;

import com.badlogic.gdx.physics.box2d.Body;
public class Platform extends StaticEntity {
    public float x, y;

    public Platform(Body body, float x, float y) {
        super(body, OBSTACLE);
        this.x = x;
        this.y = y;
    }
}
