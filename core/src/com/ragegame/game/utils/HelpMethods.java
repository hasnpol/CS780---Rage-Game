package com.ragegame.game.utils;



import com.ragegame.game.utils.Constants.*;

public class HelpMethods {
    public static UtilTypes GetTextureAtlas(EntityType entityType) {
        switch (entityType) {
            case PLAYER:
                return LoadSave.PLAYER_SPRITE;
            case BOAR:
                return LoadSave.EnemySprite.BOAR_SPRITE;
            case DRONE:
                return LoadSave.EnemySprite.DRONE_SPRITE;
            case PLANE:
                return LoadSave.EnemySprite.PLANE_SPRITE;
            case SOLDIER:
                return LoadSave.EnemySprite.SOLDIER_SPRITE;
            case COIN:
                return LoadSave.COIN_SPRITE;
            default:
                return null;
        }
    }
}
