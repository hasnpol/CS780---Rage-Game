package com.ragegame.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ragegame.game.RageGame;
import com.ragegame.game.objects.DynamicEntity.Enemy;

import java.util.ArrayList;
import java.util.Objects;


/** DESCRIPTION:
 * This file is intended to be used for loading potentially saved data such as Textures and GameData
 * DOES NOT INCLUDE LOADING VARIABLE CONSTANTS!!
 * */

public class LoadSave {
    public static class SPRITES {
        public String resPath, leftIdle, leftAnim, rightIdle, rightAnim;
        public SPRITES(String path, String leftIdle, String rightIdle, String leftAnim, String rightAnim) {
            this.resPath = path; this.leftIdle = leftIdle; this.rightIdle = rightIdle;
            this.leftAnim = leftAnim; this.rightAnim = rightAnim;
        }
        public String getTextureAtlasPath() {return this.resPath;}
        public int getFrameIndexFromDirection(Constants.Direction direction) {
            switch (direction) {
                case LEFT:
                    return 3;
                case RIGHT:
                    return 2;
                default:
                    return 0;
            }
        }
    }
    public static final String[] PLAYER_SPRITE = {"sprites/human_walk.txt",
            "human_left_idle", "human_left_jump", "human_left",
            "human_right_idle", "human_right_jump", "human_right"};
    public static final String[] SOLDIER_SPRITE = {"sprites/enemy_walk.txt",
            "enemy_left_idle", "enemy_left_jump", "enemy_left",
            "enemy_right_idle", "enemy_right_jump", "enemy_right"};

    public static final String[] COIN_SPRITE = {"sprites/coin_move.txt",
            "coin", "coin", "coin", "coin",
            "coin_shiny", "coin_shiny", "coin_shiny", "coin_shiny"};

    public static ArrayList<Object> GetEnemies() {  // TODO use for tileMap loading enemies
        // TODO should call on the enemyhandler
        return new ArrayList<>();
    }

}
