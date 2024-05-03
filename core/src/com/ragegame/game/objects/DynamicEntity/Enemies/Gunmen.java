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
    long SHOTRATE = 1000L;
    long nextShot;

    public Gunmen(Body body, SpriteBatch batch) {
        super(body, batch, SOLDIER);
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
        if (isDead()) return;
        PlayerModel player = PlayerModel.getPlayerModel();
        Vector2 playerPosition = new Vector2(player.getBody().getPosition());
        this.playerDirection = isPlayerInRange(GUNMEN_HORIZONTAL_SIGHT, GUNMEN_VERTICAL_SIGHT, getPosition());
        if (playerDirection != Direction.STOP) {
            setDirection((playerDirection == Direction.RIGHT)? Direction.LEFT : Direction.RIGHT);
            shoot(playerPosition);
        }
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion curAnimationFrame,
                     float x_position, float y_position) {
        float scale = (Game.SCALE/2);
        batch.draw(curAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE, Game.SCALE);
    }

    public void shoot(Vector2 playerPosition) {
        float offset = ((playerDirection == Direction.LEFT)) ? -0.5f : 0.5f;
        long currentTime = System.currentTimeMillis();
        if (currentTime > nextShot) {
            BulletFactory.getInstance().createBullet(getPosition().add(offset, 0), playerPosition);
            nextShot = currentTime + SHOTRATE;
        }
    }
}
