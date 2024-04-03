package com.ragegame.game.utils;

/** DESCRIPTION:
 * This file is intended to be used for constants that entities use
 * DOES NOT INCLUDE LOADING TEXTURES!!
 *  Texture and data loading should be done in the loadSave file
 * */


public class Constants {
    // TODO make an abstract GetSpriteAmount() for each standard state
    public enum Direction {
        LEFT(1), RIGHT(0);
        private final int num;
        Direction(int code) {this.num = code;}
        public int getNum() {return this.num;}
    }
    public enum State { IDLE, RUNNING, JUMPING, HIT, DEAD };
    public enum EntityType {
        PLAYER(null), ENEMY(null), OBSTACLE(null), RESOURCE(null), COIN(null);
        private Object subType;
        EntityType(Object subType) {this.subType = subType;}

        public EntityType SubType(Object subType) {
            this.subType = subType;
            return this;
        }
        public Object getSubType() {return subType;}
    }

    public static class ResourceConstants {
        // Enum Resource Type
        public enum ResType {COIN}
    }

    // TODO: add more coin types with different attributes?
    public enum CoinType {COIN}

    public static class EnemyConstants {
        // Enum Enemy Type
        public enum EnemyType {SOLDIER, BOAR, DRONE}

        // Enemy State
        public enum EnemyState {IDLE, RUNNING, ATTACK, HIT, DEAD}

        // Values for SOLDIER
        public static final int SOLDIER_WIDTH_DEFAULT = 72;
        public static final int SOLDIER_HEIGHT_DEFAULT = 32;
        public static final int SOLDIER_WIDTH = (int) (SOLDIER_WIDTH_DEFAULT * Game.SCALE);
        public static final int SOLDIER_HEIGHT = (int) (SOLDIER_HEIGHT_DEFAULT * Game.SCALE);

        // Values for BOAR
        public static final int BOAR_WIDTH_DEFAULT = 72;
        public static final int BOAR_HEIGHT_DEFAULT = 32;
        public static final int BOAR_WIDTH = (int) (BOAR_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOAR_HEIGHT = (int) (BOAR_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOAR_CHARGESPEED = 4;
        public static final int BOAR_CHARGETIME = 1000;
        public static final int BOAR_HORIZONTALSIGHT = 7;
        public static final int BOAR_VERTICALSIGHT = 2;

        // Values for BOAR
        public static final int DRONE_WIDTH_DEFAULT = 72;
        public static final int DRONE_HEIGHT_DEFAULT = 32;
        public static final int DRONE_WIDTH = (int) (BOAR_WIDTH_DEFAULT * Game.SCALE);
        public static final int DRONE_HEIGHT = (int) (BOAR_HEIGHT_DEFAULT * Game.SCALE);
        public static final float DRONE_SPEED = 0.15f; // Drone speed
        public static final float DRONE_DENSITY = 0.25f; // Drone speed
        public static final float DRONE_AMPLITUDE = 5.0f; // Amplitude of the sinusoidal movement
        public static final float DRONE_FREQUENCY = 1.5f; // Frequency of the sinusoidal movement
        public static final int DRONE_HORIZONTALSIGHT = 7; // Frequency of the sinusoidal movement
        public static final int DRONE_VERTICALSIGHT = 2; // Frequency of the sinusoidal movement

        // TODO Implement this later to easily get state from type and state
        public static int GetSpriteAmount(EnemyType enemy_type, EnemyState enemy_state) {
            switch(enemy_type) {
                case SOLDIER:
                    switch (enemy_state) {
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
                case BOAR:
                    switch (enemy_state) {
                        case IDLE:
                            return 0;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
                case DRONE:
                    switch (enemy_state) {
                        case IDLE:
                            return 0;
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

        public static final int HEALTH = 1000;
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
