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
import com.sun.tools.javac.util.Pair;

import static com.ragegame.game.utils.Constants.Direction.*;


/** DESCRIPTION:
 * This file is intended to be used for loading potentially saved data such as Textures and GameData
 * DOES NOT INCLUDE LOADING VARIABLE CONSTANTS!!
 * */

public class LoadSave {
    public static class SPRITE {
        public String resPath;
        public Map<State, String> animation = new HashMap<State, String>() {{
            put(State.IDLE, "");
            put(State.RUNNING, "");
            put(State.JUMPING, "");
            put(State.HIT, "");
            put(State.DEAD, "");
        }};
        SPRITE(String path, Map<State, String> stateList) {
            assert stateList.containsKey(State.IDLE): "SPRITE is required to have an IDLE animation";
            this.resPath = path;
            for (State state : animation.keySet()) {
                if (stateList.containsKey(state)) {
                    animation.put(state, stateList.get(state));
                } else { // Use IDLE as default
                    animation.put(state, stateList.get(State.IDLE));
                }
            }
        }
        public Array<String> flattenMap() {
            Array<String> stateArr = new Array<>();
            for (State state : State.values()) {
                stateArr.add(animation.get(state));
            }
            return stateArr;
        }
    }
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
        public static final SPRITE SOLDIER_SPRITE = new SPRITE("sprites/enemy_walk.txt",
                new HashMap<State, String>() {{
                    put(State.IDLE, "enemy_left_idle");
                    put(State.RUNNING, "enemy_left");
                    put(State.JUMPING, "enemy_left_jump");
                }}
        );
    }

    public static final String[] SNIPER_SPRITE = {"sprites/textureatlas_boar.txt",
            "boar_right_idle", "boar_left_idle"};

    public static ArrayList<Object> GetEnemies() {  // TODO use for tileMap loading enemies
        // TODO should call on the enemyhandler
        return new ArrayList<>();
    }

}
