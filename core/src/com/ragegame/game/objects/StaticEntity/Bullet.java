package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

public class Bullet extends StaticEntity {
    public Bullet(Body body, Constants.EntityType type) {
        super(body, type);
    }


}
