package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.BOAR;

public class BoarModel extends EnemyModel {


    // 0 is not charging // 1 is starting to charge // 2 is charging
    int charging;
    long chargeFinishTime;

    int playerDirection;

    int CHARGESPEED = 4;
    long CHARGETIME = 1000;

    int BOARHORIZONTALSIGHT = 7;

    int BOARVERTICALSIGHT = 2;

    public BoarModel(Body body) {
        super(body, BOAR);
        charging = 0;
        playerDirection = 0;
    }

    @Override
    public void update() {
        if (charging == 0) {
            playerDirection = EnemyModel.isPlayerInRange(BOARHORIZONTALSIGHT, BOARVERTICALSIGHT, getPosition());
            if (playerDirection != 0) {
                chargeFinishTime = System.currentTimeMillis() + CHARGETIME;
                charging = 1;
            }
        } else {
            charge();
        }
    }

    public void charge() {
        if (charging == 1 && chargeFinishTime < System.currentTimeMillis()) {
            Vector2 chargeVector = null;
            if (playerDirection == 6) {
                chargeVector = new Vector2(CHARGESPEED, 0);
            } else {
                chargeVector = new Vector2(-CHARGESPEED, 0);
            }
            setMovementVector(chargeVector);
            getBody().setLinearVelocity(chargeVector);
            charging = 2;
        } else if (charging == 2 &&  Math.abs(getBody().getLinearVelocity().x) < 0.01) {
            charging = 0;
        }
    }
}
