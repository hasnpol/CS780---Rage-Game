package com.ragegame.game.utils;

import com.ragegame.game.utils.Constants.*;

public class HelpMethods {
    public static LoadSave.SPRITE GetTextureAtlas(EntityType entityType) {
        if (entityType == EntityType.PLAYER) {
            return LoadSave.PLAYER_SPRITE;
        } else if (entityType == EntityType.ENEMY) {
            EnemyConstants.EnemyType enemyType = (EnemyConstants.EnemyType) entityType.getSubType();
            switch (enemyType) {
                case BOAR:
                    return LoadSave.EnemySprite.BOAR_SPRITE;
                case DRONE:
                    return LoadSave.EnemySprite.DRONE_SPRITE;
                default:
                    return LoadSave.EnemySprite.SOLDIER_SPRITE;
            }
        }
        return null;
    }
}
