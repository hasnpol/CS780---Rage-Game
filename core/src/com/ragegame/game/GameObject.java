package com.ragegame.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.UUID;

public class GameObject {
    private final Body body;

    private UUID id;

    private Vector2 force;

    public GameObject(Body body) {
        this.body = body;
        this.force = new Vector2();
        this.id = UUID.randomUUID();
    }

    public void setForce(Vector2 forceVector) {
        this.force = forceVector;
    }

    public UUID getId() {
        return this.id;
    }

    public Body getBody() {
        return body;
    }

    public void applyForces() {
        this.body.applyForceToCenter(this.force, true);
    }
}
