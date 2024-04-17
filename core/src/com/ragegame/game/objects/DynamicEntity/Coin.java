package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.FixtureDefinition;

import static com.ragegame.game.utils.Constants.CoinConstants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;


public class Coin extends Collectable {
    public Coin(Body body, SpriteBatch batch) {
        super(body, batch, COIN);
        this.collectableCircle.setRadius(COIN_RADIUS);
        entityFixture.density = COIN_DENSITY;
        entityFixture.isSensor = true;
        entityFixture.shape = collectableCircle;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
    }
}
