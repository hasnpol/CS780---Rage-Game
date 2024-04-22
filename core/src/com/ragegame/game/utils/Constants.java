package com.ragegame.game.utils;

/** DESCRIPTION:
 * This file is intended to be used for constants that entities use
 * DOES NOT INCLUDE LOADING TEXTURES!!
 *  Texture and data loading should be done in the loadSave file
 * */

public class Constants {
    // TODO make an abstract GetSpriteAmount() for each standard state
    public enum Direction {
        RIGHT(0), LEFT(1), STOP(2);
        private final int num;
        Direction(int code) {this.num = code;}
        public int getNum() {return this.num;}
    }
    public enum State { IDLE, RUNNING, JUMPING, HIT, DEAD, ATTACKING };

    public enum EntityType {
        PLAYER(null), ENEMY(null), OBSTACLE(null), RESOURCE(null), AMMO(null), COIN(null), MEDAL(null), BULLET(null);;
        private Object subType;
        EntityType(Object subType) {this.subType = subType;}

        public EntityType SubType(Object subType) {
            this.subType = subType;
            return this;
        }
        public Object getSubType() {return subType;}
        public boolean isSubType(Object type) {
            return (this.subType == null && this == type) // This SHOULD only happen in instance of Player type
                    || (this.subType != null && this.subType == type);
        }
        @Override
        public String toString() {
            return this.name()+": "+this.getSubType();
        }
    }

    public static final class PlayerConstants {
        public static final float WIDTH = 0.18f;
        public static final float HEIGHT = 0.45f;
        public static final float DENSITY = 2.5f;
        public static final float FRICTION = 1;
        public static final float RESTITUTION = 0.1f;
        public static final int HEALTH = 1000;
        public static final float MAXSPEED = 8f;
        public static float speed = 120F;
    }


    public static class ResourceConstants {
        // Enum Resource Type
        public enum ResType {COIN, MEDAL}
        public static final float COIN_RADIUS = .2f;
        public static final float COIN_DENSITY = 0;
    }

    public static class AmmoConstants {
        // Enum Enemy Type
        public enum AmmoType {BULLET, BOMB}
    }

    public static class EnemyConstants {
        // Enum Enemy Type
        public enum EnemyType {SOLDIER, BOAR, DRONE, PLANE}

        // Values for SOLDIER
        public static final float SOLDIER_WIDTH = 0.18f;
        public static final float SOLDIER_HEIGHT = 0.45f;
        public static final float SOLDIER_DENSITY = 2f;
        public static final float SOLDIER_FRICTION = 1;
        public static final int GUNMEN_HORIZONTAL_SIGHT = 7;
        public static final int GUNMEN_VERTICAL_SIGHT = 2;
        public static final float GUNMEN_BULLET_SPEED = 1f;

        // Values for BOAR
        public static final float BOAR_WIDTH = 0.5f;
        public static final float BOAR_HEIGHT = .32f;
        public static final float BOAR_DENSITY = .5f;
        public static final float BOAR_FRICTION = 0.2f;
        public static final int BOAR_CHARGESPEED = 4;
        public static final int BOAR_CHARGETIME = 1000;
        public static final int BOAR_HORIZONTALSIGHT = 7;
        public static final int BOAR_VERTICALSIGHT = 2;

        // Values for DRONE
        public static final float DRONE_WIDTH = 0.25f;
        public static final float DRONE_HEIGHT = 0.25f;
        public static final float DRONE_SPEED = 1.25f; // Drone speed
        public static final float DRONE_DENSITY = 0.25f; // Drone speed

        public static final float PLANE_WIDTH = 1.0f;
        public static final float PLANE_HEIGHT = 0.32f;
        public static final float PLANE_SPEED = 0.01f; // Drone speed
        public static final float PLANE_DENSITY = 0.25f; // Drone speed
        public static final float PLANE_AMPLITUDE = 5.0f; // Amplitude of the sinusoidal movement
        public static final float PLANE_FREQUENCY = 1.0f; // Frequency of the sinusoidal movement
        public static final int PLANE_HORIZONTAL_SIGHT = 2;
        public static final int PLANE_VERTICAL_SIGHT = 10;
        public static final float PLANE_BOMB_SPEED = 2f;
    }

    public static class Game {
        public static final int HEIGHT = 1000;
        public static final int WIDTH = 1000;
        public static final float SCALE = 1;
    }
}