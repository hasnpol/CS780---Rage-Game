package com.ragegame.game.handlers.contactHandlers;

import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.Platform;
import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants.*;

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
            enemyStartContact((EnemyModel) entity);
        }

        if (entity instanceof Bullet) {
            playerBulletContact((Bullet) entity);
        }
        if (entity instanceof Coin) {
            coinStartContact((Coin) entity);
        }
    }

    public void endContact(Entity entity) {
        if (entity instanceof Platform) {
            platformEndContact((Platform) entity);
        }

        if (entity instanceof EnemyModel) {
            enemyEndContact();
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

    public void enemyStartContact(EnemyModel enemyModel) {
        // Find a map for player on x-axis as well as location on y.
        // Note that getPosition returns the center of body.
        float player_x_min = playerModel.getBody().getPosition().x - (PLAYER_WIDTH / 2);
        float player_x_max = playerModel.getBody().getPosition().x + (PLAYER_WIDTH / 2);
        float player_feet = playerModel.getBody().getPosition().y - (PLAYER_HEIGHT / 2);

        // Enemy Location on X-axis
        float enemy_x_max;
        float enemy_x_min;
        float enemy_head;
        float miscalculationThres;

        if (enemyModel.type.isSubType(SOLDIER)) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (SOLDIER_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (SOLDIER_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (SOLDIER_HEIGHT / 2);
            miscalculationThres = SOLDIER_HEIGHT * 0.2f;
        } else if (enemyModel.type.isSubType(BOAR)) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (BOAR_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (BOAR_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (BOAR_HEIGHT / 2);
            miscalculationThres = BOAR_HEIGHT * 0.2f;
        } else if (enemyModel.type.isSubType(DRONE)) {
            enemy_x_min = enemyModel.getBody().getPosition().x - (DRONE_WIDTH / 2);
            enemy_x_max = enemyModel.getBody().getPosition().x + (DRONE_WIDTH / 2);
            enemy_head = enemyModel.getBody().getPosition().y + (DRONE_HEIGHT / 2);
            miscalculationThres = DRONE_HEIGHT * 0.2f;
        } else {
            System.out.println("HOW DID WE GET HERE???? ENEMY TYPE IS NOT FOUND...");
            enemy_x_min = 1f;
            enemy_x_max = 1f;
            enemy_head = 1f;
            miscalculationThres = 1f;
        }

        // Depending on the locations when collision happens, either kill enemy or get hit
        if (player_x_min > enemy_x_max || player_x_max < enemy_x_min
            || player_feet < enemy_head - miscalculationThres) {
            if (!playerModel.isImmune) {
                playerModel.isHit = true;
                //System.out.println("Player Got Hit!");
                playerModel.setHealth(-1);
                playerModel.coinsToDrop = (int) (playerModel.getCoins() * .1);
                //System.out.println("No immunity!");
                //System.out.println("\tHealth: " + playerModel.getHealth());
                //System.out.println("\tCoins: " + playerModel.getCoins());
                //System.out.println("\tCoins To Drop: " + playerModel.coinsToDrop);
            }
        } else {
            //System.out.println("Killing Enemy");
            playerModel.setGrounded(true);
            enemyModel.kill();
        }
    }

    public void playerBulletContact(Bullet bullet) {
        playerModel.setCoins((int) (-playerModel.getCoins() * .1));
        playerModel.setHealth(-10);
        playerModel.getBody().getPosition().y = -10;
        System.out.println("Player Coin: " + playerModel.getCoins());
        System.out.println("Player Health: " + playerModel.getHealth());
        if (playerModel.isDead()) {
            playerModel.kill();
        }
    }

    public void playerCoinContact(Coin collectable) {
        playerModel.setCoins(1);
        // System.out.println("Player Coin: " + playerModel.getCoins());
        collectable.setCollected();
    }

    public void enemyEndContact() {
        //System.out.println("Enemy Contact is Finished");
        if (!playerModel.isImmune && playerModel.isHit) {
            //System.out.println("No Immunity and Received HIT before, starting timer at: " + System.currentTimeMillis());
            playerModel.startTimer();
            playerModel.isImmune = true;
            playerModel.isHit = false;
            //System.out.println("Player is immune now");
        }
    }

    public void coinStartContact(Coin collectable) {
        playerModel.setCoins(1);
        collectable.setCollected();
    }
}
