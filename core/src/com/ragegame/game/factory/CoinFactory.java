package com.ragegame.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.utils.Constants;

import java.util.UUID;

public class CoinFactory {
    private static CoinFactory instance = null;
    public World world;
    public ObjectMap<UUID, Entity> gameObjectsToDestroy;
    public ObjectMap<UUID, Entity> gameObjects;

    public static synchronized CoinFactory getInstance() {
        if (instance == null) {
            instance = new CoinFactory();
        }
        return instance;
    }

    private CoinFactory() {

    }

    public void createCoin(Vector2 playerPosition, SpriteBatch batch) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 1f;
        int max = 3;
        int min = -3;
        int randomPos = (int) (Math.random() * (max - min) + min);
        bodyDef.position.set(playerPosition.x + randomPos, playerPosition.y);
        Body body = world.createBody(bodyDef);
        Coin coin = new Coin(body, batch);

        coin.entityFixture.isSensor = false;
        gameObjects.put(coin.getId(), coin);
        body.createFixture(coin.entityFixture).setUserData(coin.getId());
        coin.collectableCircle.dispose();

        // Sort of almost draws coins
    }

    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame, float x_position, float y_position) {
        float scale = (Constants.Game.SCALE/4);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Constants.Game.SCALE/2, Constants.Game.SCALE/2);
    }

}
