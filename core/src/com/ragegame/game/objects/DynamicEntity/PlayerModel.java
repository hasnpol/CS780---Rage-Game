package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.handlers.contactHandlers.PlayerContactHandler;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class PlayerModel extends DynamicEntity {
    float DRAG = 3f;
    boolean stop;
    public boolean grounded;
    final float MAXSPEED = 8f;
    long jumpPress;
    boolean sprint;
    public PlayerContactHandler playerContactHandler;
    private int health = 100;
    private int coins = 100;

    public PlayerModel(Body body) {
        super(body, PLAYER);
        stop = false;
        grounded = false;
        jumpPress = 0L;
        sprint = false;
        playerContactHandler = new PlayerContactHandler(this);
    }

    // Look at your numpad for values for directions.
    // This is called numpad notation btw and is common in fighting game discourse
    public void move(int direction) {
        if (isDead()) return;
        switch (direction) {
            case 6:
                stop = false;
                setForce(new Vector2(((sprint)) ? 15 : 7, 0));
                break;

            case 4:
                stop = false;
                setForce(new Vector2(((sprint)) ? -15 : -7, 0));
                break;

            case 5:
                stop = true;
                break;

            default:
                System.out.println("How did we get here: " + direction);
        }
    }

    public void jumpStart() {
        if (isDead()) return;
        jumpPress = System.currentTimeMillis();
    }

    public void jumpEnd() {
        if (isDead()) return;
        if (grounded) {
            getBody().applyLinearImpulse(new Vector2(0,  Math.min(8f, (System.currentTimeMillis() - jumpPress) * 0.01f)),
                    getBody().getPosition(), true);
        }
    }

    public void update() {
        Vector2 velocity = getBody().getLinearVelocity();
        if (stop) {
            setForce(velocity.set(velocity.x * -DRAG, 0));
        } else {
            if (velocity.x > MAXSPEED) {
                getBody().setLinearVelocity(MAXSPEED, velocity.y);
            } else if (velocity.x < -MAXSPEED) {
                getBody().setLinearVelocity(-MAXSPEED, velocity.y);
            }
        }
    }

    public void sprint() {
        sprint = !sprint;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        DRAG = ((grounded)) ? 6f : 0.75f;
        this.grounded = grounded;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int value) {
        /* positive value for incrementing health
           negative value for decrementing health
        */
        this.health += value;
    }

    public boolean isDead() {
        return getHealth() <= 0 || getBody().getPosition().y < 0;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int value) {
        /* positive value for incrementing coins
           negative value for decrementing coins
        */
        this.coins += value;
    }
}
