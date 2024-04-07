package com.ragegame.game.handlers.contactHandlers;

import com.badlogic.gdx.physics.box2d.World;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Platform;
import com.ragegame.game.utils.Constants;

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
        // Player Location on X and Y-axis
        float player_x_min = playerModel.getBody().getPosition().x - (Constants.PlayerConstants.PLAYER_WIDTH / 2);
        float player_x_max = playerModel.getBody().getPosition().x + (Constants.PlayerConstants.PLAYER_WIDTH / 2);
        float player_feet = playerModel.getBody().getPosition().y - (Constants.PlayerConstants.PLAYER_HEIGHT / 2);
        
        // Enemy Location on X-axis
        float enemy_x_max;
        float enemy_x_min;
        float enemy_head;
        float miscalculationThres;
        if (enemyModel.type.getSubType() == Constants.EnemyConstants.EnemyType.SOLDIER) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (Constants.EnemyConstants.SOLDIER_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (Constants.EnemyConstants.SOLDIER_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (Constants.EnemyConstants.SOLDIER_HEIGHT / 2);
            miscalculationThres = Constants.EnemyConstants.SOLDIER_HEIGHT * 0.2f;
        } else if (enemyModel.type.getSubType() == Constants.EnemyConstants.EnemyType.BOAR) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (Constants.EnemyConstants.BOAR_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (Constants.EnemyConstants.BOAR_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (Constants.EnemyConstants.BOAR_HEIGHT / 2);
            miscalculationThres = Constants.EnemyConstants.BOAR_HEIGHT * 0.2f;
        } else if (enemyModel.type.getSubType() == Constants.EnemyConstants.EnemyType.DRONE) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (Constants.EnemyConstants.DRONE_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (Constants.EnemyConstants.DRONE_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (Constants.EnemyConstants.DRONE_HEIGHT / 2);
            miscalculationThres = Constants.EnemyConstants.DRONE_HEIGHT * 0.2f;
        } else {
            System.out.println("HOW DID WE GET HERE???? ENEMY TYPE IS NOT FOUND...");
            enemy_x_min = 1f;
            enemy_x_max = 1f;
            enemy_head = 1f;
            miscalculationThres = 1f;
        }
        
        if (player_x_min > enemy_x_max || player_x_max < enemy_x_min
            || player_feet < enemy_head - miscalculationThres) {
            playerModel.setCoins((int) (-playerModel.getCoins()*.1));
            playerModel.setHealth(-50);
            if (playerModel.isDead()) {
                playerModel.kill();
            }
            //Handle this
            //createCoin();
        } else {
            playerModel.setGrounded(true);
            enemyModel.kill();
        }
    }

    public void playerCoinContact(Coin collectable) {
        playerModel.setCoins(1);
        // System.out.println("Player Coin: " + playerModel.getCoins());
        collectable.setCollected();
    }

}
