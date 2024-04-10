package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.Constants;

public class Gunmen extends EnemyModel {

    int GUNMEN_HORIZONTAL_SIGHT = 7;
    int GUNMEN_VERTICAL_SIGHT = 2;
    float GUNMEN_BULLET_SPEED = 1f;
    long SHOTRATE = 750;
    long nextShot;
    int playerDirection;


    public Gunmen(Body body) {
        super(body, Constants.EnemyConstants.EnemyType.SOLDIER);
        playerDirection = 0;
        nextShot = 0L;

    }

    @Override
    public  void update() {
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
