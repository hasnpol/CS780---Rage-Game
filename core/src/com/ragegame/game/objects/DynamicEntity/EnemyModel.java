package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class EnemyModel extends DynamicEntity {

    private Vector2 position;
    private int health;
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

    public int getHealth() {return health;}
    public Vector2 getMovementVector() {
        return movementVector;
    }
    public void setMovementVector(Vector2 movementVector) {
        this.movementVector = movementVector;
    }
}
