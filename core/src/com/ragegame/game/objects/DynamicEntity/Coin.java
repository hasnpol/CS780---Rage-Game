package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

public class Coin extends Collectable {
    public Coin(Body body) {
        super(body, Constants.EntityType.COIN);
    }

}
