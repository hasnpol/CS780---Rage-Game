package com.ragegame.game.utils;
import static java.util.Map.entry;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ragegame.game.RageGame;
import com.ragegame.game.objects.DynamicEntity.Enemy;

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
        public Map<State, HashMap<Direction, String>> animation = new HashMap<State, HashMap<Direction, String>>() {{
            put(State.IDLE, new HashMap<Direction, String>() {{put(LEFT, "");put(RIGHT, "");}});
            put(State.RUNNING, new HashMap<Direction, String>() {{put(LEFT, "");put(RIGHT, "");}});
            put(State.JUMPING, new HashMap<Direction, String>() {{put(LEFT, "");put(RIGHT, "");}});
            put(State.HIT, new HashMap<Direction, String>() {{put(LEFT, "");put(RIGHT, "");}});
            put(State.DEAD, new HashMap<Direction, String>() {{put(LEFT, "");put(RIGHT, "");}});
        }};
        SPRITE(String path, Map<State, HashMap<Direction, String>> stateList) {
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
                stateArr.add(animation.get(state).get(LEFT));
                stateArr.add(animation.get(state).get(RIGHT));
            }
            return stateArr;
        }
        public int getAnimFromState(Constants.Direction direction) {
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
    public static final SPRITE PLAYER_SPRITE = new SPRITE("sprites/human_walk.txt",
            new HashMap<State, HashMap<Direction, String>>() {{
                put(State.IDLE, new HashMap<Direction, String>() {{put(LEFT, "human_left_idle");put(RIGHT, "human_right_idle");}});
                put(State.RUNNING, new HashMap<Direction, String>() {{put(LEFT, "human_left");put(RIGHT, "human_right");}});
                put(State.JUMPING, new HashMap<Direction, String>() {{put(LEFT, "human_left_jump");put(RIGHT, "human_right_jump");}});
            }}
    );

    public static final SPRITE BOAR_SPRITE = new SPRITE("sprites/boar_animation.txt",
            new HashMap<State, HashMap<Direction, String>>() {{
                put(State.IDLE, new HashMap<Direction, String>() {{put(LEFT, "boar_left_idle");put(RIGHT, "boar_right_idle");}});
            }}
    );
    public static final SPRITE SOLDIER_SPRITE = new SPRITE("sprites/enemy_walk.txt",
            new HashMap<State, HashMap<Direction, String>>() {{
                put(State.IDLE, new HashMap<Direction, String>() {{put(LEFT, "enemy_left_idle");put(RIGHT, "enemy_right_idle");}});
                put(State.RUNNING, new HashMap<Direction, String>() {{put(LEFT, "enemy_left");put(RIGHT, "enemy_right");}});
                put(State.JUMPING, new HashMap<Direction, String>() {{put(LEFT, "enemy_left_jump");put(RIGHT, "enemy_right_jump");}});
            }}
    );

    public static final String[] SNIPER_SPRITE = {"sprites/boar_animation.txt",
            "boar_right_idle", "boar_left_idle"};

    public static ArrayList<Object> GetEnemies() {  // TODO use for tileMap loading enemies
        // TODO should call on the enemyhandler
        return new ArrayList<>();
    }

}
