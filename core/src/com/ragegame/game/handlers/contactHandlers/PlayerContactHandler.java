package com.ragegame.game.handlers.contactHandlers;

import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.Medal;

import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.Platform;

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
            platformStartContact();
        }

        if (entity instanceof Enemy) {
            enemyStartContact((Enemy) entity);
        }

        if (entity instanceof Bullet) {
            playerBulletContact();
        }
        if (entity instanceof Coin) {
            coinStartContact((Coin) entity);
        }
        if (entity instanceof Medal) {
            playerMedalContact((Medal) entity);
        }
    }

    public void endContact(Entity entity) {
        if (entity instanceof Platform) {
            platformEndContact();
        }

        if (entity instanceof Enemy) {
            enemyEndContact();
        }
    }

    public void platformStartContact() {
        if (Objects.equals(playerFixtureType, "feet")) {
            playerModel.groundTouchCount += 1;
        }
    }

    public void platformEndContact() {
        if (Objects.equals(playerFixtureType, "feet")) {
            playerModel.groundTouchCount -= 1;
        }
    }

    public void enemyStartContact(Enemy enemyModel) {
        if (Objects.equals(playerFixtureType, "feet") && Objects.equals(entityFixtureType, "head")) {
            enemyModel.kill();
        } else {
            playerModel.isHit = true;
            playerModel.coinsToDrop = (int) ((playerModel.getCoins() * .1));
            playerModel.setHealth(-100);
        }
    }

    public void playerBulletContact() {
        playerModel.setCoins((int) (-playerModel.getCoins() * .1));
        playerModel.setHealth(-100);
        playerModel.getBody().getPosition().y = -10;
    }

    public void playerMedalContact(Medal collectable) {
        playerModel.setMedal(1);
        playerModel.voidRestoreHealth();
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
