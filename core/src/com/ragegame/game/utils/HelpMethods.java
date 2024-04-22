
package com.ragegame.game.utils;

import com.ragegame.game.utils.Constants.*;

public class HelpMethods {
    public static UtilTypes GetTextureAtlas(EntityType entityType) {
        switch (entityType) {
            case PLAYER:
                return LoadSave.PLAYER_SPRITE;
            case ENEMY:
                switch ((EnemyConstants.EnemyType) entityType.getSubType()) {
                    case BOAR:
                        return LoadSave.EnemySprite.BOAR_SPRITE;
                    case DRONE:
                        return LoadSave.EnemySprite.DRONE_SPRITE;
                    case PLANE:
                        return LoadSave.EnemySprite.PLANE_SPRITE;
                    default:
                        return LoadSave.EnemySprite.SOLDIER_SPRITE;
                }
            case RESOURCE:
                switch ((ResourceConstants.ResType) entityType.getSubType()) {
                    case COIN:
                        return LoadSave.COIN_SPRITE;
                    default:
                        return LoadSave.COIN_SPRITE;
                }
            case AMMO:
                switch ((AmmoConstants.AmmoType) entityType.getSubType()) {
                    case BOMB:
                        return LoadSave.EnemySprite.BOMB_SPRITE;
                    default:
                        return LoadSave.EnemySprite.BULLET_SPRITE;
                }

            default:
                return null;
        }
    }
}
