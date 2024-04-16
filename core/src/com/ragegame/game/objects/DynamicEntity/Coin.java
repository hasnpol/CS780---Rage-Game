package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

import static com.ragegame.game.utils.Constants.EnemyConstants.BOAR_DENSITY;
import static com.ragegame.game.utils.Constants.EnemyConstants.BOAR_FRICTION;
import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_DENSITY;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_RADIUS;
import static com.ragegame.game.utils.Constants.ResourceConstants.ResType.COIN;
import com.ragegame.game.utils.UtilTypes;

public class Coin extends Collectable {
    public Coin(Body body, SpriteBatch batch) {
        super(body, batch, RESOURCE.SubType(COIN));
        this.collectableCircle.setRadius(COIN_RADIUS);
        entityFixture.density = COIN_DENSITY;
        entityFixture.isSensor = true;
        entityFixture.shape = collectableCircle;
        this.getBody().createFixture(entityFixture).setUserData(this.getId());
    }
}
