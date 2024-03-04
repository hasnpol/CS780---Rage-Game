package com.ragegame.game.objects.StaticEntity;

import static com.ragegame.game.utils.Constants.EntityType.OBSTACLE;

import com.badlogic.gdx.physics.box2d.Body;

public class Platform extends StaticEntity {
    public Platform(Body body) {
        super(body, OBSTACLE);
    }
}
