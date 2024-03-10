package com.ragegame.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ragegame.game.RageGame;
import com.ragegame.game.objects.DynamicEntity.Enemy;

import java.util.ArrayList;
import java.util.Objects;

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

//    public static ArrayList<Object> GetEnemies() {
//        ArrayList<Enemy> list = new ArrayList<>();
//        for (int j = 0; j < Gdx.graphics.getHeight(); j++)
//            for (int i = 0; i < Gdx.graphics.getWidth(); i++) {
//                Color color = new Color(Gdx.graphics.);
//                int value = color.getGreen();
//            }
//        BodyDef enemyBodyDef = new BodyDef();
//        enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
//        enemyBodyDef.position.set(new Vector2(0, 10f));
//        Body russianBody = RageGame.world.createBody(enemyBodyDef);
//        list.add(new Russian(russianBody));
//        return list;
//    }

}
