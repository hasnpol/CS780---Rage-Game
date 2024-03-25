package com.ragegame.game.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.utils.LoadSave;

//import com.badlogic.gdx.Graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class EnemyHandler {
    private BufferedImage[][] russianArr;
    private Texture[][] russianArr_;
    private ArrayList<Object> enemies = new ArrayList<>();
    public EnemyHandler() {
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
//        enemies = LoadSave.GetEnemies();
    }

//    public void update() {
//        for (Object e : enemies) {
//            (e (Enemy)).update();
//        }
//    }

//    public void draw(Graphics g) {
//        drawRussians(g);
//    }
//
//    private void drawRussians(Graphics g) {
//        float dt = g.getDeltaTime();
//        for (Russian r : russians) {
//            batch.draw(russianArr[r.getEnemyState()][r.getAniIndex()], (int) r.getHitbox().x, -1, RUSSIAN_WIDTH, RUSSIAN_HEIGHT);
//            g.drawImage(russianArr[r.getEnemyState()][r.getAniIndex()], (int) r.getHitbox().x, RUSSIAN, RUSSIAN_WIDTH, RUSSIAN_HEIGHT, null);
//        }
//    }

    private void loadEnemyImages() {
        // 5 different types of russians
        russianArr = new BufferedImage[5][9];
        russianArr_ = new Texture[5][9];
//        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.RUSSIAN_SPRITE[0]);
//        Texture temp_ = LoadSave.GetSpriteAtlas(LoadSave.RUSSIAN_SPRITE)
//        for (int j = 0; j < russianArr.length; j++) {
//            for (int i = 0; i < russianArr[j].length; i++) {
//                russianArr[j][i] = temp.getSubimage(RUSSIAN_WIDTH_DEFAULT, RUSSIAN_HEIGHT_DEFAULT, RUSSIAN_WIDTH_DEFAULT, RUSSIAN_HEIGHT_DEFAULT);
//            }
//        }
    }
}
