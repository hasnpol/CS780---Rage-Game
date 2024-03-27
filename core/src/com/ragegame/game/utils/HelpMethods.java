package com.ragegame.game.utils;

import com.ragegame.game.utils.Constants.EntityType;
import com.ragegame.game.utils.Constants.EnemyType;

public class HelpMethods {
    public static LoadSave.SPRITE GetTextureAtlas(EntityType entityType) {
        if (entityType == EntityType.PLAYER) {
            return LoadSave.PLAYER_SPRITE;
        } else if (entityType == EntityType.ENEMY) {
            EnemyType enemyType = (EnemyType) entityType.getType();
            switch (enemyType) {
//                case SNIPER:
//                    return LoadSave.SNIPER_SPRITE;
                case BOAR:
                    return LoadSave.BOAR_SPRITE;
                default:
                    return LoadSave.SOLDIER_SPRITE;
            }
        }
        return null;
    }
}
