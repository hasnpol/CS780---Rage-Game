package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.StaticEntity.StaticEntity;
import com.ragegame.game.utils.Constants;

public class Bullet extends DynamicEntity {
    public Bullet(Body body, SpriteBatch batch) {
        super(body, batch, Constants.EntityType.BULLET);
    }


}
