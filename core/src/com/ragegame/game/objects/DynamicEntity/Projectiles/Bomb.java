package com.ragegame.game.objects.DynamicEntity.Projectiles;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.ragegame.game.utils.Constants.*;

import com.ragegame.game.objects.DynamicEntity.Projectile;
import com.ragegame.game.utils.FixtureDefinition;

public class Bomb extends Projectile {

    public Bomb(Body body, SpriteBatch batch, Vector2 initialPos, Vector2 destination) {
        super(body, batch, ProjectileType.BOMB);
        this.objectBox = (PolygonShape) new PolygonShape();
        ((PolygonShape) this.objectBox).setAsBox(BOMB_WIDTH * Game.SCALE, BOMB_HEIGHT * Game.SCALE);
        entityFixture.density = 0;
        entityFixture.shape = ((PolygonShape) this.objectBox);
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
        this.getBody().setLinearVelocity(getVelocity(initialPos, destination, PLANE_BOMB_SPEED));
    }

    @Override
    public void update(SpriteBatch batch) {
        this.setDirection((this.getBody().getLinearVelocity().x > 0)? Direction.LEFT : Direction.RIGHT);
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion curAnimationFrame,
                     float x_position, float y_position) {
        if (!markedForDelete) {
            if (this.isAttacking()) {
                float scale = (Game.SCALE*2);
                batch.draw(curAnimationFrame, x_position - (scale/2), y_position - (scale/4),
                        scale, scale);
            } else {
                float scale = (Game.SCALE/3);
                batch.draw(curAnimationFrame, x_position - scale, y_position - (scale),
                        scale * 2, scale*2);
            }
        }
    }
}
