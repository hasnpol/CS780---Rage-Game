package com.ragegame.game.handlers.contactHandlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Platform;
import com.ragegame.game.objects.view.View;

public class PlayerContactHandler {
    PlayerModel playerModel;

    public PlayerContactHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void startContact(Entity entity) {

        if (entity instanceof Platform) {
            platformStartContact((Platform) entity);
        }

        if (entity instanceof EnemyModel) {
            playerEnemyContact((EnemyModel) entity);
        }

        if (entity instanceof Coin) {
            playerCoinContact((Coin) entity);
        }

    }

    public void endContact(Entity entity, World world) {
        if (entity instanceof Platform) {
            platformEndContact((Platform) entity);
        }
    }

    public void platformStartContact(Platform platform) {
        if(playerModel.getBody().getPosition().y > platform.y) {
            playerModel.setGrounded(true);
        }
    }

    public void platformEndContact(Platform platform) {
        if (playerModel.getBody().getPosition().y > platform.y) {
            playerModel.setGrounded(false);
        }
    }

    public void playerEnemyContact(EnemyModel enemyModel) {
        float player_x_min = playerModel.getBody().getPosition().x - (playerModel.getHeight() / 2);
        float player_x_max = playerModel.getBody().getPosition().x + (playerModel.getHeight() / 2);
        float enemy_x_min = enemyModel.getBody().getPosition().x - (enemyModel.getHeight() / 2);
        float enemy_x_max = enemyModel.getBody().getPosition().x + (enemyModel.getHeight() / 2);

        // Figure out this
//        if (player_x_min > enemy_x_max || player_x_max < enemy_x_min
//            || playerModel.getBody().getPosition().y - playerModel.getHeight() / 2
//                <= enemyModel.getBody().getPosition().y + enemyModel.getHeight() / 2) {
//            playerModel.setCoins((int) (-playerModel.getCoins()*.1));
//            playerModel.setHealth(-50);
//            if (playerModel.isDead()) {
//                playerModel.kill();
//            }
//            //Handle this
//            //createCoin();
//        } else {
//            playerModel.setGrounded(true);
//            enemyModel.kill();
//        }
    }

    public void playerCoinContact(Coin collectable) {
        playerModel.setCoins(1);
        // System.out.println("Player Coin: " + playerModel.getCoins());
        collectable.setCollected();
    }

}
