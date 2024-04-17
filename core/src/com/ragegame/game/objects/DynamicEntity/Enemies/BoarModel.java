package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.utils.FixtureDefinition;

import static com.ragegame.game.utils.Constants.BoarConstants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;

public class BoarModel extends EnemyModel {


    // 0 is not charging // 1 is starting to charge // 2 is charging
    int charging;
    long chargeFinishTime;

    int playerDirection;

    public BoarModel(Body body, SpriteBatch batch) {
        super(body, batch, BOAR);
        charging = 0;
        playerDirection = 0;
        enemyBox.setAsBox(BOAR_WIDTH, BOAR_HEIGHT);
        entityFixture.density = BOAR_DENSITY;
        entityFixture.friction = BOAR_FRICTION;
        entityFixture.restitution = .1f;
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(.15f, .05f, new Vector2(0, .45f), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));

    }

    @Override
    public void update(SpriteBatch batch) {
        if (charging == 0) {
            playerDirection = EnemyModel.isPlayerInRange(BOAR_HORIZONTALSIGHT, BOAR_VERTICALSIGHT, getPosition());
            if (playerDirection != 0) {
                chargeFinishTime = System.currentTimeMillis() + BOAR_CHARGETIME;
                charging = 1;
            }
        } else {
            charge();
        }
    }

    public void charge() {
        if (charging == 1 && chargeFinishTime < System.currentTimeMillis()) {
            Vector2 chargeVector = new Vector2((playerDirection == 6)?
                    BOAR_CHARGESPEED: -BOAR_CHARGESPEED, 0);
            setMovementVector(chargeVector);
            getBody().setLinearVelocity(chargeVector);
            charging = 2;
        } else if (charging == 2 &&  Math.abs(getBody().getLinearVelocity().x) < 0.01) {
            charging = 0;
        }
    }
}
