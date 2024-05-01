package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Projectile extends DynamicEntity {

    public Projectile(Body body, SpriteBatch batch, ProjectileType projectileType) {
        super(body, batch, PROJECTILE.SubType(projectileType));
    }

    public Vector2 getVelocity(Vector2 initial, Vector2 destination, float speed) {
        return new Vector2((destination.x - initial.x) * speed, (destination.y - initial.y) * speed);
    }
}
