package com.ragegame.game.objects.DynamicEntity.Enemies;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.FixtureDefinition;

import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.SoldierConstants.*;

public class Gunmen extends EnemyModel {
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
        enemyBox.setAsBox(SOLDIER_WIDTH, SOLDIER_HEIGHT);
        entityFixture.density = SOLDIER_DENSITY;
        entityFixture.friction = SOLDIER_FRICTION;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(.15f, .05f, new Vector2(0, .45f), 0);
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
