package com.ragegame.game.handlers.contactHandlers;

import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.Platform;

import static com.ragegame.game.utils.Constants.BoarConstants.*;
import static com.ragegame.game.utils.Constants.DroneConstants.*;
import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.PlayerConstants.*;
import static com.ragegame.game.utils.Constants.SoldierConstants.*;

import java.util.Objects;

public class PlayerContactHandler {
    PlayerModel playerModel;
    public String playerFixtureType;
    public String entityFixtureType;

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
        if(Objects.equals(playerFixtureType, "feet")) {
            playerModel.setGrounded(true);
        }
    }

    public void platformEndContact(Platform platform) {
        if (Objects.equals(playerFixtureType, "feet")) {
            playerModel.setGrounded(false);
        }
    }

    public void enemyStartContact(EnemyModel enemyModel) {
        if (Objects.equals(playerFixtureType, "feet") && Objects.equals(entityFixtureType, "head")) {
            enemyModel.kill();
        } else {
            System.out.println("Hit");
            playerModel.isHit = true;
            playerModel.coinsToDrop = (int) (playerModel.getCoins() * .1);
        }
    }

    public void playerBulletContact(Bullet bullet) {
        playerModel.setCoins((int) (-playerModel.getCoins() * .1));
        playerModel.setHealth(-10);
        playerModel.getBody().getPosition().y = -10;
    }

    public void playerCoinContact(Coin collectable) {
        playerModel.setCoins(1);
        // System.out.println("Player Coin: " + playerModel.getCoins());
        collectable.setCollected();
    }

    public void enemyEndContact() {
        playerModel.isHit = false;
    }

    public void coinStartContact(Coin collectable) {
        playerModel.setCoins(1);
        collectable.setCollected();
    }
}
