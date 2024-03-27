package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class EnemyModel extends DynamicEntity {
    private Vector2 position;
    private int health = 100;
    private float speed = 120F;
    private Vector2 movementVector = new Vector2(0, 0);

    public EnemyModel(Body body) {
        super(body, ENEMY);
        this.position = body.getPosition();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void updatePosition(float dt) {
        Vector2 posChange = this.movementVector.cpy().scl(speed * dt);
        this.position.add(posChange);
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
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
}
