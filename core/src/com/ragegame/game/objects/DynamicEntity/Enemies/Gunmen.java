package com.ragegame.game.objects.DynamicEntity.Enemies;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.SOLDIER;

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
        this.getBody().createFixture(entityFixture).setUserData(this.getId());
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
