package com.ragegame.game.objects.actors;

import static com.ragegame.game.utils.Constants.EntityType.OBSTACLE;

import com.badlogic.gdx.physics.box2d.Body;

public class Platform extends Actors {
    public Platform(Body body) {
        super(body, OBSTACLE);
    }
}
