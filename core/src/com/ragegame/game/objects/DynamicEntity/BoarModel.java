package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

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
        super(body);
        charging = 0;
        playerDirection = 0;
    }

    @Override
    public void update() {
        if (charging == 0) {
            PlayerModel playerModel = PlayerModel.getPlayerModel();
            if (playerModel != null) {
                if (Math.abs(playerModel.getBody().getPosition().x - getPosition().x) < BOARHORIZONTALSIGHT && Math.abs(playerModel.getBody().getPosition().y - getPosition().y) < BOARVERTICALSIGHT) {
                    charging = 1;
                    chargeFinishTime = System.currentTimeMillis() + CHARGETIME;
                    if (playerModel.getBody().getPosition().x > getPosition().x) {
                        playerDirection = 6;
                    } else {
                        playerDirection = 4;
                    }
                }
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
        }
    }
}
