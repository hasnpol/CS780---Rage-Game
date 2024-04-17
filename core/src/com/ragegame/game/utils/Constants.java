package com.ragegame.game.utils;

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
        PLAYER,
        OBSTACLE,
        RESOURCE,
        COIN,
        DRONE,
        BOAR,
        SOLDIER,
        PLANE;
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
        public static final float PLAYER_WIDTH = 0.18f; //0.18f
        public static final float PLAYER_HEIGHT = 0.45f; // 0.45f
        public static final float PLAYER_DENSITY = 2.5f;
        public static final float PLAYER_FRICTION = 1;
        public static final float PLAYER_RESTITUTION = 0.1f;
    }

    public class CoinConstants {
        public static final float COIN_RADIUS = .2f;
        public static final float COIN_DENSITY = 0;
    }

    public class PlaneConstants {
        public static final int PLANE_WIDTH_DEFAULT = 162;
        public static final int PLANE_HEIGHT_DEFAULT = 32;
        public static final float PLANE_WIDTH = 1.0f;
        public static final float PLANE_HEIGHT = 0.32f;
        public static final float PLANE_SPEED = 0.01f; // Drone speed
        public static final float PLANE_DENSITY = 0.25f; // Drone speed
        public static final float PLANE_AMPLITUDE = 5.0f; // Amplitude of the sinusoidal movement
        public static final float PLANE_FREQUENCY = 1.0f; // Frequency of the sinusoidal movement
        public static final int PLANE_HORIZONTALSIGHT = 7; // Frequency of the sinusoidal movement
        public static final int PLANE_VERTICALSIGHT = 2; // Frequency of the sinusoidal movement

    }

    public static class DroneConstants {
        // Values for DRONE
        public static final int DRONE_WIDTH_DEFAULT = 4;
        public static final int DRONE_HEIGHT_DEFAULT = 4;
        public static final float DRONE_WIDTH = 0.25f;
        public static final float DRONE_HEIGHT = 0.25f;
        public static final float DRONE_SPEED = 0.01f; // Drone speed
        public static final float DRONE_DENSITY = 0.25f; // Drone speed
        public static final float DRONE_AMPLITUDE = 5.0f; // Amplitude of the sinusoidal movement
        public static final float DRONE_FREQUENCY = 1.0f; // Frequency of the sinusoidal movement
        public static final int DRONE_HORIZONTALSIGHT = 7; // Frequency of the sinusoidal movement
        public static final int DRONE_VERTICALSIGHT = 2; // Frequency of the sinusoidal movement
    }

    public static class BoarConstants {
        // Values for BOAR
        public static final int BOAR_WIDTH_DEFAULT = 72;
        public static final int BOAR_HEIGHT_DEFAULT = 32;
        public static final float BOAR_WIDTH = 0.5f;
        public static final float BOAR_HEIGHT = .32f;
        public static final float BOAR_DENSITY = .5f;
        public static final float BOAR_FRICTION = 0.2f;
        public static final int BOAR_CHARGESPEED = 4;
        public static final int BOAR_CHARGETIME = 1000;
        public static final int BOAR_HORIZONTALSIGHT = 7;
        public static final int BOAR_VERTICALSIGHT = 2;
    }

    public static class SoldierConstants {
        // Values for SOLDIER
        public static final int SOLDIER_WIDTH_DEFAULT = 72;
        public static final int SOLDIER_HEIGHT_DEFAULT = 32;
        public static final float SOLDIER_WIDTH = 0.18f;
        public static final float SOLDIER_HEIGHT = 0.45f;
        public static final float SOLDIER_DENSITY = 2f;
        public static final float SOLDIER_FRICTION = 1;
    }
    public static class Game {
        public static final int HEIGHT = 800;
        public static final int WIDTH = 1000;
        public static final int SCALE = 1/16;
    }
}
