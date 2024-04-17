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

    private Vector2 position;
    private int health = 100;
    private float speed = 120F;
    private Vector2 movementVector = new Vector2(0, 0);
    private int enemyState;
    public boolean isDead;

    public Enemy(Body body, SpriteBatch batch, EnemyConstants.EnemyType enemyType) {
        super(body, batch, ENEMY.SubType(enemyType));
        this.enemyBox = new PolygonShape();
        this.position = body.getPosition();
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {}

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

    public void kill() {
        this.isDead = true;
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
