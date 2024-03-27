package com.ragegame.game.utils;

import com.ragegame.game.utils.Constants.EntityType;
import com.ragegame.game.utils.Constants.EnemyType;

public class HelpMethods {
    public static String[] GetTextureAtlas(EntityType entityType) {
        if (entityType == EntityType.PLAYER) {
            return LoadSave.PLAYER_SPRITE;
        } else if (entityType == EntityType.ENEMY) {
            EnemyType enemyType = (EnemyType) entityType.getSubType();
            return LoadSave.SOLDIER_SPRITE;
        } else if (entityType == EntityType.COIN) {
            return LoadSave.COIN_SPRITE;
        }
        return null;
    }
}
