package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.ragegame.game.utils.FixtureDefinition;

import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.BOAR;


public class Boar extends Enemy {
    // 0 is not charging // 1 is starting to charge // 2 is charging
    int charging;
    long chargeFinishTime;

    public Boar(Body body, SpriteBatch batch) {
        super(body, batch, BOAR);
        charging = 0;
        enemyBox.setAsBox(BOAR_WIDTH * Game.SCALE, BOAR_HEIGHT * Game.SCALE);
        entityFixture.density = BOAR_DENSITY;
        entityFixture.friction = BOAR_FRICTION;
        entityFixture.restitution = .1f;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(BOAR_WIDTH * Game.SCALE, .05f,
                new Vector2(0, (BOAR_HEIGHT + .05f) * Game.SCALE), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position) {
        float scale = (Game.SCALE/2);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE, Game.SCALE);
    }

    @Override
    public void update(SpriteBatch batch) {
        if (charging == 0) {
            playerDirection = Enemy.isPlayerInRange(BOAR_HORIZONTALSIGHT, BOAR_VERTICALSIGHT, getPosition());
            if (playerDirection != Direction.STOP) {
                chargeFinishTime = System.currentTimeMillis() + BOAR_CHARGETIME;
                charging = 1;
            }
        } else {
            charge();
        }
    }

    public void charge() {
        if (charging == 1 && chargeFinishTime < System.currentTimeMillis()) {
            Vector2 chargeVector = new Vector2((playerDirection == Direction.RIGHT)?
                    BOAR_CHARGESPEED: -BOAR_CHARGESPEED, 0);
            setMovementVector(chargeVector);
            getBody().setLinearVelocity(chargeVector);
            charging = 2;
        } else if (charging == 2 &&  Math.abs(getBody().getLinearVelocity().x) < 0.01) {
            charging = 0;
        }
    }
}
