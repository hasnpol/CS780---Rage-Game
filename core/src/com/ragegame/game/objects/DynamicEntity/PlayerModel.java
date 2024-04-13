package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.handlers.contactHandlers.PlayerContactHandler;
import com.ragegame.game.factory.CoinFactory;

import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants.*;
import static com.ragegame.game.utils.Constants.*;


public class PlayerModel extends DynamicEntity {
    private static PlayerModel playerModel = null;
    float DRAG = 3f;
    boolean stop;
    public boolean grounded;
    long jumpPress;
    boolean sprint;
    public PlayerContactHandler playerContactHandler;
    private int health = HEALTH;
    private int coins = 100;
    private int medals = 0;
    public boolean isHit;
    public boolean isImmune;
    long startTime = 0;
    long endTime;
    public int coinsToDrop = 0;

    public PlayerModel(Body body) {
        super(body, PLAYER);
        stop = false;
        grounded = false;
        jumpPress = 0L;
        sprint = false;
        playerModel = this;
        isHit = false;
        isImmune = false;
    }

    public void setPlayerContactHandler() {
        this.playerContactHandler = new PlayerContactHandler(this);
    }

    public static PlayerModel getPlayerModel() {
        return playerModel;
    }

    // Look at your numpad for values for directions.
    // This is called numpad notation btw and is common in fighting game discourse
    public void move(int direction) {
        if (isDead()) return;
        switch (direction) {
            case 6:
                stop = false;
                setForce(new Vector2(15, 0));
                setDirection(Direction.LEFT);
                setForce(new Vector2(((sprint)) ? 15 : 7, 0));
                break;

            case 4:
                stop = false;
                setForce(new Vector2(-15, 0));
                setDirection(Direction.RIGHT);
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

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void update() {
        if (playerModel.isDead()) {
            //System.out.println("Player DIED!");
            playerModel.kill();
        } else {
            if (playerModel.isImmune) {
                //System.out.println("Player has immune, checking time");
                endTime = System.currentTimeMillis();
                //System.out.println("Player has immune, end time: " + endTime );
                if ((endTime - startTime) / 1000 == 10) {
                    playerModel.isImmune = false;
                    //System.out.println("Player immune is GONE ");
                }
            }
            if (playerModel.isHit && !playerModel.isImmune) {
                //System.out.println("Player has hit and no immune");
                dropCoin();
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
    }

    public void sprint() {
        sprint = !sprint;
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
    public int getMedals() {return this.medals;}

    public void setCoins(int value) {
        this.coins += value;
    }

    public void setMedal(int value) {
        this.medals += value;
    }

    public void kill() {
        this.markedForDelete = true;
    }

    public void dropCoin() {
        PlayerModel playerModel = PlayerModel.getPlayerModel();
        if (playerModel != null) {
            for (int i = 0; i < playerModel.coinsToDrop; i++) {
                CoinFactory.getInstance().createCoin(playerModel.getBody().getPosition());
            }
        }
    }

}
