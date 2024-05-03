package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.utils.Constants;

import static com.ragegame.game.utils.Constants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class Enemy extends DynamicEntity {
    public PolygonShape enemyBox;
    public Direction playerDirection;
    private Vector2 position;
    private int health = 100;
    private float speed = 120F;
    private Vector2 movementVector = new Vector2(0, 0);

    public Enemy(Body body, SpriteBatch batch, EnemyConstants.EnemyType enemyType) {
        super(body, batch, ENEMY.SubType(enemyType));
        playerDirection = Direction.STOP;
        this.enemyBox = new PolygonShape();
        this.position = body.getPosition();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
        setDirection((movementVector.x > 0)? Direction.LEFT : Direction.RIGHT);
    }

    public void kill() {
        this.state = State.DEAD; // Make sure the player is dead
        this.markedForDelete = true;
    }

    // Method to calculate the seek behavior towards the player
    // Also points enemy in player direction
    public Vector2 seek(Vector2 target, Vector2 curPosition, float speed, float deltaTime) {
        this.setDirection((target.x > curPosition.x)? Direction.LEFT : Direction.RIGHT);
        Vector2 desired = target.sub(getBody().getPosition());
        desired.setLength(speed * deltaTime);
        return desired;
    }

    public static Direction isPlayerInRange(int horizontal, int vertical, Vector2 position) {
        PlayerModel playerModel = PlayerModel.getPlayerModel();
        if (playerModel != null) {
            if (Math.abs(playerModel.getBody().getPosition().x - position.x) < horizontal && Math.abs(playerModel.getBody().getPosition().y - position.y) < vertical) {
                if (playerModel.getBody().getPosition().x > position.x) {
                    return Direction.RIGHT;
                } else {
                    return Direction.LEFT;
                }
            }
        }
        return Direction.STOP;
    }

    public void update(float dt) {
        Vector2 enemyPosition = new Vector2(this.getBody().getPosition());
        if (enemyPosition.x > 0) {
            setDirection(Direction.LEFT);
            this.setMovementVector(new Vector2(1, 0));
        } else {
            setDirection(Direction.RIGHT);
            this.setMovementVector(new Vector2(-1, 0));
        }
    }
}
