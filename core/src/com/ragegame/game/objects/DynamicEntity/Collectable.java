package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.ragegame.game.utils.Constants;

public abstract class Collectable extends DynamicEntity {
    public CircleShape collectableCircle;
    public boolean isCollected;

    public Collectable(Body body, SpriteBatch batch, Constants.EntityType entityType) {
        super(body, batch, entityType);
        collectableCircle = new CircleShape();
        this.isCollected = false;
    }

    public void setCollected() {
        this.isCollected = true;
        this.markedForDelete = true;
    }
}
