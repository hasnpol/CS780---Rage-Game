package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

public class Bomb extends DynamicEntity {

    public Bomb(Body body, SpriteBatch batch) {
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
