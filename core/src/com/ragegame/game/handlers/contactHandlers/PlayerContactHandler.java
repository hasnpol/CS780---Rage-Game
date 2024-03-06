package com.ragegame.game.handlers.contactHandlers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Platform;

public class PlayerContactHandler {
    PlayerModel playerModel;

    public PlayerContactHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void startContact(Entity entity) {

        if (entity instanceof Platform) {
            platformStartContact((Platform) entity);
        }

    }

    public void endContact(Entity entity) {
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



}
