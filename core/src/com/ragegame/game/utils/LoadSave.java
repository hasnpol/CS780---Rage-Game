package com.ragegame.game.utils;

import static com.ragegame.game.utils.Constants.EnemyConstants.RUSSIAN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.ragegame.game.RageGame;
import com.ragegame.game.objects.actors.Russian;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LoadSave {
    public static final String[] PLAYER_SPRITE =
            {"sprites/human_walk.txt", "human_0", "human_1", "human_2"};
    public static final String[] RUSSIAN_SPRITE =
            {"sprites/human_walk_sheet_directional.txt", "enemy_0", "enemy_1", "enemy_2"};

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Russian> GetRussians() {
        ArrayList<Russian> list = new ArrayList<>();
//        for (int j = 0; j < Gdx.graphics.getHeight(); j++)
//            for (int i = 0; i < Gdx.graphics.getWidth(); i++) {
//                Color color = new Color(Gdx.graphics.);
//                int value = color.getGreen();
//            }
        BodyDef enemyBodyDef = new BodyDef();
        enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
        enemyBodyDef.position.set(new Vector2(0, 10f));
        Body russianBody = RageGame.world.createBody(enemyBodyDef);
        list.add(new Russian(russianBody));
        return list;
    }

}
