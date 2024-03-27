package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

public abstract class Collectable extends DynamicEntity {
    public boolean isCollected;

    public Collectable(Body body, Constants.EntityType entityType) {
        super(body, entityType);
        this.isCollected = false;
    }

    public void setCollected() {
        this.isCollected = true;
        this.markedForDelete = true;
    }
}
