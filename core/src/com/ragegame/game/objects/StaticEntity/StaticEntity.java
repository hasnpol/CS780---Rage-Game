package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.utils.Constants;

public class StaticEntity extends Entity {
    public StaticEntity(Body body, Constants.EntityType type) {
        super(body, type);
    }
}
