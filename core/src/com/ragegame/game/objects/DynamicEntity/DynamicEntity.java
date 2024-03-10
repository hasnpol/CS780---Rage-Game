package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.utils.Constants;

public class DynamicEntity extends Entity {
    private Constants.Direction direction = Constants.Direction.RIGHT;

    public DynamicEntity(Body body, Constants.EntityType type) {
        super(body, type);
    }

    public Constants.Direction getDirection() {
        return this.direction;
    }
}
