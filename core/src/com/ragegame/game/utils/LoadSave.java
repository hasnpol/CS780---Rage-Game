package com.ragegame.game.utils;
import static java.util.Map.entry;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ragegame.game.RageGame;

import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.UtilTypes.*;


/** DESCRIPTION:
 * This file is intended to be used for loading potentially saved data such as Textures and GameData
 * DOES NOT INCLUDE LOADING VARIABLE CONSTANTS!!
 * */

public class LoadSave {
    public static final SPRITE PLAYER_SPRITE = new SPRITE("sprites/textureatlas_human.txt",
            new HashMap<State, String>() {{
                put(State.IDLE, "idle");
                put(State.RUNNING, "running");
                put(State.JUMPING, "jumping");
            }}
    );

    public static class EnemySprite {
        public static final SPRITE BOAR_SPRITE = new SPRITE("sprites/textureatlas_boar.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "idle");
                }}
        );
        public static final SPRITE DRONE_SPRITE = new SPRITE("sprites/textureatlas_drone.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "idle");
                }}
        );
        public static final SPRITE PLANE_SPRITE = new SPRITE("sprites/textureatlas_plane.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "idle");
                    put(State.RUNNING, "running");
                    put(State.ATTACKING, "dropping");
                }}
        );
        public static final SPRITE BULLET = new SPRITE("sprites/textureatlas_bullet.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "idle");
                }}
        );
        public static final SPRITE SOLDIER_SPRITE = new SPRITE("sprites/enemy_walk.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "enemy_left_idle");
                    put(State.RUNNING, "enemy_left");
                    put(State.JUMPING, "enemy_left_jump");
                }}
        );
    }

    public static final COLLECTABLE COIN_SPRITE = new COLLECTABLE("sprites/coin_move.txt",
            new ArrayList<String>() {{
                add("coin");
                add("coin_shiny");
            }}
    );

    public static final String[] SNIPER_SPRITE = {"sprites/textureatlas_boar.txt",
            "boar_right_idle", "boar_left_idle"};

    public static ArrayList<Object> GetEnemies() {  // TODO use for tileMap loading enemies
        // TODO should call on the enemyhandler
        return new ArrayList<>();
    }

}
