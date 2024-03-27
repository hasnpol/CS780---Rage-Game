package com.ragegame.game.utils;

/** DESCRIPTION:
 * This file is intended to be used for constants that entities use
 * DOES NOT INCLUDE LOADING TEXTURES!!
 *  Texture and data loading should be done in the loadSave file
 * */


public class Constants {
    // TODO make an abstract GetSpriteAmount() for each standard state
    public enum Direction {
        LEFT(6), RIGHT(4), UP(0);
        private final int num;
        Direction(int code) {this.num = code;}
        public int getNum() {return this.num;}
    }
    public enum State { IDLE, RUNNING, JUMPING, HIT, DEAD };
    public enum EntityType {
        PLAYER(null), ENEMY(null), OBSTACLE(null), COIN(null);
        private Object subType;
        EntityType(Object subType) {this.subType = subType;}
        public void setEnemySubType(Object enemySubType) {
            if (this == ENEMY) {this.subType = enemySubType;}
            else {throw new UnsupportedOperationException("Cannot set subtype for non-ENEMY EntityType");}
        }
        public Object getSubType() {return subType;}
    }

    public enum EnemyType {SOLDIER, SNIPER;}

    // TODO: add more coin types with different attributes?
    public enum CoinType {COIN}

    public static class EnemyConstants {
        // Enum Enemy Type
        public static final int SOLDIER = 0;

        // Enemy State
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        // Values for SOLDIER
        public static final int SOLDIER_WIDTH_DEFAULT = 72;
        public static final int SOLDIER_HEIGHT_DEFAULT = 32;

        public static final int SOLDIER_WIDTH = (int) (SOLDIER_WIDTH_DEFAULT * Game.SCALE);
        public static final int SOLDIER_HEIGHT = (int) (SOLDIER_HEIGHT_DEFAULT * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            if (enemy_type == SOLDIER) {
                switch (enemy_type) {
                    case IDLE:
                        return 9;
                    case RUNNING:
                        return 6;
                    case ATTACK:
                        return 7;
                    case HIT:
                        return 4;
                    case DEAD:
                        return 5;
                }
            }
            return 0;
        }
    }

    public static class PlayerConstants {
        // Player State
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK = 6;

        public static final float MAXSPEED = 8f;
        public static float speed = 120F;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

    public static class Game {
        public static final int HEIGHT = 800;
        public static final int WIDTH = 1000;
        public static final int SCALE = 1/16;
    }
}
