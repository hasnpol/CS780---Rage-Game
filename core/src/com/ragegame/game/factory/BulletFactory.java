package com.ragegame.game.factory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.DynamicEntity.Projectiles.Bullet;

import java.util.ArrayList;
import java.util.UUID;

public class BulletFactory {
    private static BulletFactory instance = null;
    public World world;
    public ObjectMap<UUID, Entity> gameObjectsToDestroy;
    public ObjectMap<UUID, Entity> gameObjects;
    public ArrayList<DynamicEntity> dynamicEntities = new ArrayList<>();
    public SpriteBatch batch;

    public static synchronized BulletFactory getInstance() {
        if (instance == null) {
            instance = new BulletFactory();
        }
        return instance;
    }

    private BulletFactory() {}

    public void createBullet(Vector2 initialPos, Vector2 destination) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(initialPos);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(0);
        Bullet bullet = new Bullet(body, batch, initialPos, destination);
        gameObjects.put(bullet.getId(), bullet);

        dynamicEntities.add(bullet);
        ((CircleShape) bullet.objectBox).dispose();
    }
}
