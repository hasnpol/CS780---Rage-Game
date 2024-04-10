package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

import static com.ragegame.game.utils.Constants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class EnemyModel extends DynamicEntity {
    private Vector2 position;
    private int health = 100;
    private float speed = 120F;
    private Vector2 movementVector = new Vector2(0, 0);
    private int enemyState;

    public EnemyModel(Body body, EnemyConstants.EnemyType enemyType) {
        super(body, ENEMY.SubType(enemyType));
        this.position = body.getPosition();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void updatePosition(float dt) {
        Vector2 posChange = this.movementVector.cpy().scl(speed * dt);
        this.position.add(posChange);
        if (this.type == Constants.EntityType.ENEMY && this.type.getSubType() == Constants.EnemyConstants.EnemyType.DRONE) {
            System.out.println("Could set direction here?: " + ((this.getDirection().getNum() == 1)? "Left": "right"));
        }
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
        setDirection((movementVector.x > 0)? Direction.LEFT : Direction.RIGHT);
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

    public void kill() {
        this.markedForDelete = true;
    }

    public static int isPlayerInRange(int horizontal, int vertical, Vector2 position) {
        PlayerModel playerModel = PlayerModel.getPlayerModel();
        if (playerModel != null) {
            if (Math.abs(playerModel.getBody().getPosition().x - position.x) < horizontal && Math.abs(playerModel.getBody().getPosition().y - position.y) < vertical) {
                if (playerModel.getBody().getPosition().x > position.x) {
                    return 6;
                } else {
                    return 4;
                }
            }
        }
        return 0;
    }

    public void update() {
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
