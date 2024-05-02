package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.handlers.contactHandlers.PlayerContactHandler;
import com.ragegame.game.utils.FixtureDefinition;

import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants.*;
import static com.ragegame.game.utils.Constants.*;

public class PlayerModel extends DynamicEntity {
    public PolygonShape playerBox;
    private static PlayerModel playerModel = null;
    float DRAG = 3f;
    boolean stop;
    public boolean grounded;
    long jumpPress;
    boolean sprint;
    public PlayerContactHandler playerContactHandler;
    private int health = HEALTH;
    private int coins = 0;
    private int medals = 0;
    public boolean isHit;
    public boolean isImmune;
    long startTime = 0;
    long endTime;
    public int coinsToDrop = 0;
    public int groundTouchCount = 0;
    public boolean atGoal = false;

    public PlayerModel(Body body, SpriteBatch batch) {
        super(body, batch, PLAYER);
        stop = false;
        grounded = false;
        jumpPress = 0L;
        sprint = false;
        playerModel = this;
        isHit = false;
        isImmune = false;

        this.playerBox = new PolygonShape();
        playerBox.setAsBox(WIDTH * Game.SCALE, HEIGHT * Game.SCALE);
        entityFixture.density = DENSITY; // more density -> bigger mass for the same size
        entityFixture.friction = FRICTION;
        entityFixture.restitution = RESTITUTION;
        entityFixture.shape = playerBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        playerBox.setAsBox(WIDTH * Game.SCALE, .05f,
                new Vector2(0, -(HEIGHT + .05f) * Game.SCALE), 0);
        entityFixture.shape = playerBox;
        entityFixture.isSensor = true;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "feet"));

        this.setPlayerContactHandler();
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position) {
        float scale = (Game.SCALE/2);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale, Game.SCALE, Game.SCALE);
    }

    public void setPlayerContactHandler() {
        this.playerContactHandler = new PlayerContactHandler(this);
    }

    public static PlayerModel getPlayerModel() {
        return playerModel;
    }

    // Look at your numpad for values for directions.
    // This is called numpad notation btw and is common in fighting game discourse
    public void move(Direction direction) {
        if (this.isDead()) return;
        stop = direction == Direction.STOP;
        switch (direction) {
            case RIGHT:
                setForce(new Vector2(15, 0));
                setDirection(Direction.LEFT);
                setForce(new Vector2(((sprint)) ? 15 : 7, 0));
                break;
            case LEFT:
                setForce(new Vector2(-15, 0));
                setDirection(Direction.RIGHT);
                setForce(new Vector2(((sprint)) ? -15 : -7, 0));
                break;
            case STOP:
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
            getBody().applyLinearImpulse(new Vector2(0,  Math.min(8.1f, (System.currentTimeMillis() - jumpPress) * 0.01f)),
                    getBody().getPosition(), true);
        }
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void update(SpriteBatch batch) {
        playerModel.setGrounded(playerModel.groundTouchCount > 0);

        Vector2 curPosition = playerModel.getBody().getPosition();
        if ((curPosition.x < -0.5 || curPosition.y < 0) || this.isDead()) {
            playerModel.kill();
            return;
        }

        if (playerModel.isImmune) {
            endTime = System.currentTimeMillis();
            if ((endTime - startTime) / 1000 == 1) {
                playerModel.isImmune = false;
            }
        }

        if (playerModel.isHit && !playerModel.isImmune) {
            playerModel.isImmune = true;
            playerModel.startTimer();
        }

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

    public void setGrounded(boolean grounded) {
        DRAG = ((grounded)) ? 6f : 0.75f;
        this.grounded = grounded;
        if (!grounded) {
            this.state = State.JUMPING;
        } else {
            handleStateChange();
        }
    }

    public int getHealth() {return this.health;}

    public void voidRestoreHealth() {
        this.health = HEALTH;
    }

    public void setHealth(int value) {
        /* positive value for incrementing health
           negative value for decrementing health
        */
        this.health += value;
        if (this.health <= 0) this.kill();
    }

    public int getCoins() {
        return this.coins;
    }
    public int getMedals() {return this.medals;}

    public float getX() {return this.getBody().getPosition().x;}
    public float getY() {return this.getBody().getPosition().y;}

    public void setCoins(int value) {
        this.coins += value;
    }

    public void setMedal(int value) {
        this.medals += value;
    }

    public void kill() {
        this.state = State.DEAD; // Make sure the player is dead
        this.markedForDelete = true;
    }

}
