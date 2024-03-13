package com.ragegame.game.handlers.contactHandlers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Platform;

public class PlayerContactHandler {
    PlayerModel playerModel;

    public PlayerContactHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void startContact(Entity entity, World world) {

        if (entity instanceof Platform) {
            platformStartContact((Platform) entity);
        }

//        if (entity instanceof EnemyModel) {
//            playerEnemyContact((EnemyModel) entity, world);
//        }
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

//    public void playerEnemyContact(EnemyModel enemyModel, World world) {
//        if (playerModel.getBody().getPosition().y > enemyModel.getBody().getPosition().y) {
//            playerModel.setGrounded(true);
//            enemyModel.setHealth(-100);
//            world.destroyBody(enemyModel.getBody());
//            System.out.println("Enemy Health: " + enemyModel.getHealth());
//        } else {
//            playerModel.setCoins((int) (-playerModel.getCoins()*.1));
//            playerModel.setHealth(-10);
//            playerModel.getBody().getPosition().y = -10;
//            System.out.println("Player Coin: " + playerModel.getCoins());
//            System.out.println("Player Health: " + playerModel.getHealth());
//        }
//    }

}