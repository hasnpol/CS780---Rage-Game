package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EntityType.RESOURCE;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_DENSITY;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_RADIUS;
import static com.ragegame.game.utils.Constants.ResourceConstants.ResType.MEDAL;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.FixtureDefinition;

public class Medal extends Collectable {
    public Medal(Body body, SpriteBatch batch) {
        super(body, batch, RESOURCE.SubType(MEDAL));

        this.collectableCircle.setRadius(0.5f);
        entityFixture.density = COIN_DENSITY;
        entityFixture.isSensor = true;
        entityFixture.shape = collectableCircle;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
    }
}
