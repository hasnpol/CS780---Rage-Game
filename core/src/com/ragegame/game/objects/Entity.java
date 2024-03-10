package com.ragegame.game.objects;

import java.awt.Graphics;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

public class Entity {
    /// This super class is used for game objects that has physics
    public final EntityType type;
    private final Body body;
    protected Rectangle2D.Float hitbox;
    private UUID id;
    private Vector2 force;

    private Vector2 movementVector = new Vector2(0, 0);
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

    protected void drawHitbox(Graphics g) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }



}
