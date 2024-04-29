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
    public State state;
    private final Body body;
    protected float width;
    protected float height;
    private UUID id;
    private Vector2 force;
    private Vector2 movementVector = new Vector2(0, 0);
    public boolean markedForDelete = false;
    public long attackTime = System.currentTimeMillis();

    public Entity(Body body, EntityType type) {
        this.body = body;
        this.force = new Vector2();
        this.id = UUID.randomUUID();
        this.type = type;
        this.state = State.IDLE;
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

    public Vector2 getMovementVector() {return movementVector;}
    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
        handleStateChange();
    }

    public void handleStateChange() {
        if (this.movementVector.x != 0) {this.state = State.RUNNING;}
        else {this.state = State.IDLE;}
    }

    public boolean isDead() {return this.state == State.DEAD;}
    public boolean isAttacking() {return this.state == State.ATTACKING;}

    public boolean needsDeletion() {
        return this.markedForDelete;
    }

    public void delete(World world) {
        world.destroyBody(body);
    }

}
