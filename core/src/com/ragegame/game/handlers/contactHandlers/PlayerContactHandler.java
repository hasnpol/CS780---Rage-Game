package com.ragegame.game.handlers.contactHandlers;

import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.Platform;
import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants;

public class PlayerContactHandler {
    PlayerModel playerModel;

    public PlayerContactHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void startContact(Entity entity) {

        if (entity instanceof Platform) {
            platformStartContact((Platform) entity);
        }

        if (entity instanceof Enemy) {
            enemyStartContact((Enemy) entity);
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

        if (entity instanceof Enemy) {
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

    public void enemyStartContact(Enemy enemy) {
        // Find a map for player on x-axis as well as location on y.
        // Note that getPosition returns the center of body.
        float player_x_min = playerModel.getBody().getPosition().x - (PlayerConstants.WIDTH / 2);
        float player_x_max = playerModel.getBody().getPosition().x + (PlayerConstants.WIDTH / 2);
        float player_feet = playerModel.getBody().getPosition().y - (PlayerConstants.HEIGHT / 2);

        // Enemy Location on X-axis
        float enemy_x_max;
        float enemy_x_min;
        float enemy_head;
        float miscalculationThres;
        float enemy_width = 0;
        float enemy_height = 0;

        if (enemy.type.isSubType(SOLDIER)) {
            enemy_width = SOLDIER_WIDTH;
            enemy_height = SOLDIER_HEIGHT;
        } else if (enemy.type.isSubType(BOAR)) {
            enemy_width = BOAR_WIDTH;
            enemy_height = BOAR_HEIGHT;
        } else if (enemy.type.isSubType(DRONE)) {
            enemy_width = DRONE_WIDTH;
            enemy_height = DRONE_HEIGHT;
        } else if (enemy.type.isSubType(PLANE)) {
            enemy_width = PLANE_WIDTH;
            enemy_height = PLANE_HEIGHT;
        }
        if (enemy_height == 0) {
            System.out.println("HOW DID WE GET HERE???? ENEMY TYPE IS NOT FOUND...");
            enemy_x_min = 1f;
            enemy_x_max = 1f;
            enemy_head = 1f;
            miscalculationThres = 1f;
        } else {
            enemy_x_min = enemy.getBody().getPosition().x - (enemy_width / 2);
            enemy_x_max = enemy.getBody().getPosition().x + (enemy_width / 2);
            enemy_head = enemy.getBody().getPosition().y + (enemy_height / 2);
            miscalculationThres = enemy_height * 0.2f;
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
            enemy.kill();
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
