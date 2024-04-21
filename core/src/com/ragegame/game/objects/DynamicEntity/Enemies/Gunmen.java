package com.ragegame.game.objects.DynamicEntity.Enemies;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;

import com.ragegame.game.utils.FixtureDefinition;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.SOLDIER;


public class Gunmen extends Enemy {
    int GUNMEN_HORIZONTAL_SIGHT = 7;
    int GUNMEN_VERTICAL_SIGHT = 2;
    float GUNMEN_BULLET_SPEED = 1f;
    long SHOTRATE = 750;
    long nextShot;
    int playerDirection;

    public Gunmen(Body body, SpriteBatch batch) {
        super(body, batch, SOLDIER);
        playerDirection = 0;
        nextShot = 0L;
        enemyBox.setAsBox(SOLDIER_WIDTH * Game.SCALE, SOLDIER_HEIGHT * Game.SCALE);
        entityFixture.density = SOLDIER_DENSITY;
        entityFixture.friction = SOLDIER_FRICTION;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(SOLDIER_WIDTH * Game.SCALE, .05f,
                new Vector2(0, (SOLDIER_HEIGHT + .05f) * Game.SCALE), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));

    }

    @Override
    public  void update(SpriteBatch batch) {
        if (isDead) {
            return;
        }
        playerDirection = isPlayerInRange(GUNMEN_HORIZONTAL_SIGHT, GUNMEN_VERTICAL_SIGHT, getPosition());
        if (playerDirection != 0) {
            shoot();
        }
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        float scale = (Game.SCALE/2);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE, Game.SCALE);
    }

    public void shoot() {
        PlayerModel playerModel = PlayerModel.getPlayerModel();
        if (playerModel != null) {
            float offset = ((playerDirection == 4)) ? -0.5f : 0.5f;
            long currentTime = System.currentTimeMillis();
            if (currentTime > nextShot) {
                BulletFactory.getInstance().createBullet(getPosition().add(offset, 0), playerModel.getBody().getPosition(), GUNMEN_BULLET_SPEED);
                nextShot = currentTime + SHOTRATE;
            }
        }
    }
}
