package com.ragegame.game.utils;

import com.ragegame.game.utils.Constants.EntityType;
import com.ragegame.game.utils.Constants.EnemyType;

public class HelpMethods {
    public static String[] GetTextureAtlas(EntityType entityType) {
        if (entityType == EntityType.PLAYER) {
            return LoadSave.PLAYER_SPRITE;
        } else if (entityType == EntityType.ENEMY) {
            EnemyType enemyType = (EnemyType) entityType.getSubType();
            if (enemyType == EnemyType.RUSSIAN) {
                return LoadSave.RUSSIAN_SPRITE;
            } else {
                return LoadSave.RUSSIAN_SPRITE;
            }
        }
        return null;
    }
}
