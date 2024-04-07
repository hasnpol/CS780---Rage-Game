package com.ragegame.game.objects;

import java.awt.Graphics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.utils.Constants.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

public class Entity {
    /// This super class is used for game objects that has physics
    public final EntityType type;
    private final Body body;
    protected float width;
    protected float height;
    private UUID id;
    private Vector2 force;

    private Vector2 movementVector = new Vector2(0, 0);

    public boolean markedForDelete = false;
    public Entity(Body body, EntityType type) {
        this.body = body;
        this.force = new Vector2();
        this.id = UUID.randomUUID();
        this.type = type;
    }
    public void setForce(Vector2 forceVector) {
        this.force = forceVector;
    }
    public UUID getId() {
        return this.id;
    }
    public Body getBody() {
        return this.body;
    }
    public void applyForces() {
        this.body.applyForceToCenter(this.force, true);
    }

    public void updatePosition(float dt) {
        Vector2 posChange = this.movementVector.cpy().scl(PlayerConstants.speed * dt);
        this.body.getPosition().add(posChange);
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
    }

    protected void setHeight(float height) {
        this.height = height;
    }

    protected void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public boolean needsDeletion() {
        return this.markedForDelete;
    }

    public void delete(World world) {
        world.destroyBody(body);
    }

}
