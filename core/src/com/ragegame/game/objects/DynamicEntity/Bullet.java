package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.FixtureDefinition;

public class Bullet extends DynamicEntity {

    public Bullet(Body body, SpriteBatch batch, Vector2 initialPos, Vector2 destination) {
        super(body, batch, Constants.EntityType.BULLET);
        this.objectBox = (CircleShape) new CircleShape();
        ((CircleShape) this.objectBox).setRadius(GUNMEN_BULLET_RADIUS * Constants.Game.SCALE);
        entityFixture.density = 0;
        entityFixture.shape = ((CircleShape) this.objectBox);
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
        this.getBody().setLinearVelocity(getVelocity(initialPos, destination, GUNMEN_BULLET_SPEED));
    }

    public Vector2 getVelocity(Vector2 initial, Vector2 destination, float speed) {
        return new Vector2((destination.x - initial.x) * speed, (destination.y - initial.y) * speed);
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        float scale = (Constants.Game.SCALE/4);
        if (! markedForDelete) {
            batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                    Constants.Game.SCALE/2, Constants.Game.SCALE/2);
        }
    }
}
