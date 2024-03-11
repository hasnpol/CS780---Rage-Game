package com.ragegame.game.objects.DynamicEntity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.handlers.contactHandlers.PlayerContactHandler;

import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants.*;
import static com.ragegame.game.utils.Constants.*;

public class PlayerModel extends DynamicEntity {
    float DRAG = 3f;
    boolean stop;

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        DRAG = ((grounded)) ? 6f : 0.75f;
        this.grounded = grounded;
    }

    public boolean grounded;

    long jumpPress;
    boolean sprint;
    boolean dead;

    private Direction direction = Direction.RIGHT;

    public Direction getDirection() {
        return this.direction;
    }

    public PlayerContactHandler playerContactHandler;
    public PlayerModel(Body body) {
        super(body, PLAYER);
        stop = false;
        grounded = false;
        jumpPress = 0L;
        sprint = false;
        dead = false;

        playerContactHandler = new PlayerContactHandler(this);
    }

    //Look at your numpad for values for directions.
    // This is called numpad notation btw and is common in fighting game discourse
    public void move(int direction) {
        if (dead) return;
        switch (direction) {
            case 6:
                stop = false;
                setForce(new Vector2(15, 0));
                this.direction = Direction.LEFT;
                setForce(new Vector2(((sprint)) ? 15 : 7, 0));
                break;

            case 4:
                stop = false;
                setForce(new Vector2(-15, 0));
                this.direction = Direction.RIGHT;
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
        if (dead) return;
        jumpPress = System.currentTimeMillis();
    }

    public void jumpEnd() {
        if (dead) return;
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

        if (getBody().getPosition().y < 0) {
            dead = true;
        }
    }

    public void sprint() {
        sprint = !sprint;
    }
}
