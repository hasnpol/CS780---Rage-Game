package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_DENSITY;
import static com.ragegame.game.utils.Constants.ResourceConstants.COIN_RADIUS;
import static com.ragegame.game.utils.Constants.ResourceConstants.ResType.COIN;

import com.ragegame.game.utils.FixtureDefinition;
import com.ragegame.game.utils.UtilTypes;

public class Coin extends Collectable {
    public Coin(Body body, SpriteBatch batch) {
        super(body, batch, RESOURCE.SubType(COIN));
        this.collectableCircle.setRadius(COIN_RADIUS * Game.SCALE);
        entityFixture.density = COIN_DENSITY;
        entityFixture.isSensor = true;
        entityFixture.shape = collectableCircle;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        float scale = (Game.SCALE/4);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE/2, Game.SCALE/2);
    }
}