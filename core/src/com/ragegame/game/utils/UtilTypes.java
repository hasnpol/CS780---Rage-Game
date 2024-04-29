package com.ragegame.game.utils;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilTypes {
    public String resPath;
    public ArrayList<String> animations = new ArrayList<String>();

    UtilTypes(String path, ArrayList<String> animations) {
        this.resPath = path;
        this.animations = animations;
    }

    public static class SPRITE extends UtilTypes {
        public Map<Constants.State, String> Sprite_animations = new HashMap<Constants.State, String>() {{
            put(Constants.State.IDLE, "");
            put(Constants.State.RUNNING, "");
            put(Constants.State.JUMPING, "");
            put(Constants.State.HIT, "");
            put(Constants.State.DEAD, "");
            put(Constants.State.ATTACKING, "");
        }};
        SPRITE(String path, Map<Constants.State, String> stateList) {
            super(path, null);
            assert stateList.containsKey(Constants.State.IDLE): "SPRITE is required to have an IDLE animation";
            for (Constants.State state : Sprite_animations.keySet()) {
                if (stateList.containsKey(state)) {
                    Sprite_animations.put(state, stateList.get(state));
                } else { // Use IDLE as default
                    Sprite_animations.put(state, stateList.get(Constants.State.IDLE));
                }
            }
            ArrayList<String> cur_animations = new ArrayList<>();
            for (Constants.State state : Constants.State.values()) {
                cur_animations.add(Sprite_animations.get(state));
            }
            this.animations = cur_animations;
        }
    }

    public static class COLLECTABLE extends UtilTypes {
        COLLECTABLE(String path, ArrayList<String> stateList) {
            super(path, stateList);
        }
    }
}
