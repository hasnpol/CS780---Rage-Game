package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.DRONE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.StaticEntity.StaticEntity;
import com.ragegame.game.utils.Constants;

public class Bullet extends DynamicEntity {
    public Bullet(Body body, SpriteBatch batch) {
        super(body, batch, Constants.EntityType.ENEMY);
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
