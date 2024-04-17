package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.handlers.contactHandlers.PlayerContactHandler;
import com.ragegame.game.factory.CoinFactory;

import static com.ragegame.game.utils.Constants.EnemyConstants.BOAR_HEIGHT;
import static com.ragegame.game.utils.Constants.EnemyConstants.BOAR_WIDTH;
import static com.ragegame.game.utils.Constants.EnemyConstants.PLANE_DENSITY;
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
    private int coins = 100;
    public boolean isHit;
    public boolean isImmune;
    long startTime = 0;
    long endTime;
    public int coinsToDrop = 0;

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
        this.getBody().createFixture(entityFixture).setUserData(this.getId());
        this.setPlayerContactHandler();
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        float scale = (Game.SCALE/2) * new_scale;
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE * new_scale, Game.SCALE * new_scale);
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
        if (isDead()) return;
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

    @Override
    public void update(SpriteBatch batch) {
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
                dropCoin(batch);
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

    public float getX() {return this.getBody().getPosition().x;}
    public float getY() {return this.getBody().getPosition().y;}

    public void setCoins(int value) {
        /* positive value for incrementing coins
           negative value for decrementing coins
        */
        this.coins += value;
    }

    public void kill() {
        this.markedForDelete = true;
    }

    public void dropCoin(SpriteBatch batch) {
        PlayerModel playerModel = PlayerModel.getPlayerModel();
        if (playerModel != null) {
            for (int i = 0; i < playerModel.coinsToDrop; i++) {
                CoinFactory.getInstance().createCoin(playerModel.getBody().getPosition(), batch);
            }
        }
    }

}
