package com.ragegame.game.objects.DynamicEntity.Projectiles;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.ragegame.game.objects.DynamicEntity.Projectile;
import com.ragegame.game.utils.FixtureDefinition;

public class Bullet extends Projectile {

    public Bullet(Body body, SpriteBatch batch, Vector2 initialPos, Vector2 destination) {
        super(body, batch, ProjectileType.BULLET);
        this.objectBox = (CircleShape) new CircleShape();
        ((CircleShape) this.objectBox).setRadius(GUNMEN_BULLET_RADIUS * Game.SCALE);
        entityFixture.density = 0;
        entityFixture.shape = ((CircleShape) this.objectBox);
        this.getBody().setGravityScale(0);
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
        this.getBody().setLinearVelocity(getVelocity(initialPos, destination, GUNMEN_BULLET_SPEED));
    }

    @Override
    public void update(SpriteBatch batch) {
        // TODO THERE IS A WIERD GRAVITY ISSUE!
        //  This happens when the player's position is under a gunman's position (or to the right of the gunman), the bullet will have gravity
        this.setDirection((this.getBody().getLinearVelocity().x > 0)? Direction.LEFT : Direction.RIGHT);
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position) {
        float scale = (Game.SCALE/4);
        if (! markedForDelete) {
            batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                    Game.SCALE/2, Game.SCALE/2);
        }
    }
}
