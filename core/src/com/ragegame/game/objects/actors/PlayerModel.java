package com.ragegame.game.objects.actors;

import static java.lang.Math.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class PlayerModel extends Actors {
    float DRAG = 3f;
    boolean stop;

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        DRAG = ((grounded)) ? 3f : 0.5f;
        this.grounded = grounded;
    }

    public boolean grounded;
    final float MAXSPEED = 8f;

    long jumpPress;

    public PlayerModel(Body body) {
        super(body);
        stop =false;
        grounded = false;
        jumpPress = 0L;
    }

    //Look at your numpad for values for directions.
    // This is called numpad notation btw and is common in fighting game discourse
    public void move(int direction) {
        switch (direction) {
            case 6:
                stop = false;
                setForce(new Vector2(15, 0));
                break;

            case 4:
                stop = false;
                setForce(new Vector2(-15, 0));
                break;

            case 5:
                stop = true;
                break;

            default:
                System.out.println("How did we get here: " + direction);
        }
    }

    public void jumpStart() {
        jumpPress = System.currentTimeMillis();
    }

    public void jumpEnd() {
        if (grounded) {
            getBody().applyLinearImpulse(new Vector2(0,  Math.min(6f, (System.currentTimeMillis() - jumpPress) * 0.5f)),
                    getBody().getPosition(), true);
        }
    }

    public void update() {
        Vector2 velocity = getBody().getLinearVelocity();
        if (stop) {
            setForce(velocity.set(velocity.x * -DRAG, velocity.y));
        } else {
            if (velocity.x > MAXSPEED) {
                getBody().setLinearVelocity(MAXSPEED, velocity.y);
            } else if (velocity.x < -MAXSPEED) {
                getBody().setLinearVelocity(-MAXSPEED, velocity.y);
            }
        }

    }
}
