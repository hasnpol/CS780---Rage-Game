package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;

public class Gunmen extends EnemyModel {

    int GUNMEN_HORIZONTAL_SIGHT = 7;
    int GUNMEN_VERTICAL_SIGHT = 2;
    int GUNMEN_BULLET_SPEED = 3;
    long SHOTRATE = 500;
    long nextShot;
    int playerDirection;


    public Gunmen(Body body) {
        super(body);
        playerDirection = 0;
        nextShot = 0L;

    }

    @Override
    public  void update() {
        playerDirection = isPlayerInRange(GUNMEN_HORIZONTAL_SIGHT, GUNMEN_HORIZONTAL_SIGHT, getPosition());
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
