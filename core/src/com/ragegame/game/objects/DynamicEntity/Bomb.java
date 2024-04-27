package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EnemyConstants.PLANE_HEIGHT;
import static com.ragegame.game.utils.Constants.EnemyConstants.PLANE_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.FixtureDefinition;

public class Bomb extends DynamicEntity {

    public Bomb(Body body, SpriteBatch batch, Vector2 initialPos, Vector2 destination, float speed) {
        super(body, batch, Constants.EntityType.BOMB);
        this.objectBox = (PolygonShape) new PolygonShape();
        ((PolygonShape) this.objectBox).setAsBox((PLANE_WIDTH / 4) * Constants.Game.SCALE,
                (PLANE_HEIGHT / 2) * Constants.Game.SCALE);
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
        float scale = (Constants.Game.SCALE/4);
        if (! markedForDelete) {
            batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                    Constants.Game.SCALE/2, Constants.Game.SCALE/2);
        }
    }
}
