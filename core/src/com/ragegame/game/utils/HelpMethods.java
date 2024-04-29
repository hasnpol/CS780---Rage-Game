
package com.ragegame.game.utils;

import static com.ragegame.game.utils.Constants.EntityType.ENEMY;

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
            case BULLET:
                return LoadSave.EnemySprite.BULLET;
            case BOMB:
                return LoadSave.EnemySprite.BOMB_SPRITE;
            case RESOURCE:
                switch ((ResourceConstants.ResType) entityType.getSubType()) {
                    case COIN:
                        return LoadSave.COIN_SPRITE;
                    default:
                        return LoadSave.COIN_SPRITE;
                }
            default:
                return null;
        }
    }

//    public static UtilTypes GetEndTextureAtlas(EntityType entityType) {
//        switch (entityType) {
//            case BOMB:
//                return LoadSave.EnemySprite.BOMB_SPRITE;
//        }
//    }
}
