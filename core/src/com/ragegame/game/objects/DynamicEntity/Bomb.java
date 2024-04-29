package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.ragegame.game.utils.Constants.*;
import com.ragegame.game.utils.FixtureDefinition;

public class Bomb extends DynamicEntity {

    public Bomb(Body body, SpriteBatch batch, Vector2 initialPos, Vector2 destination, float speed) {
        super(body, batch, EntityType.BOMB);
        this.objectBox = (PolygonShape) new PolygonShape();
        ((PolygonShape) this.objectBox).setAsBox(BOMB_WIDTH * Game.SCALE,
                BOMB_HEIGHT * Game.SCALE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.shape = ((PolygonShape) this.objectBox);
        this.getBody().createFixture(fixtureDef).setUserData(new FixtureDefinition(this.getId(), "body"));
        this.getBody().setLinearVelocity(getVelocity(initialPos, destination, speed));

    }

    public Vector2 getVelocity(Vector2 initial, Vector2 destination, float speed) {
        return new Vector2((destination.x - initial.x) * speed, (destination.y - initial.y) * speed);
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        if (!markedForDelete) {
            if (this.isAttacking()) {
                float scale = (Game.SCALE*2);
                batch.draw(currentAnimationFrame, x_position - (scale/2), y_position - (scale/4),
                        scale, scale);
            } else {
                float scale = (Game.SCALE/3);
                batch.draw(currentAnimationFrame, x_position - scale, y_position - (scale),
                        scale * 2, scale*2);
            }
        }
    }
}
