package com.ragegame.game.factory;

import static com.ragegame.game.utils.Constants.EnemyConstants.PLANE_HEIGHT;
import static com.ragegame.game.utils.Constants.EnemyConstants.PLANE_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.DynamicEntity.Bomb;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.FixtureDefinition;

import java.util.ArrayList;
import java.util.UUID;

public class BombFactory {
    private static BombFactory instance = null;
    public World world;
    public ObjectMap<UUID, Entity> gameObjectsToDestroy;
    public ObjectMap<UUID, Entity> gameObjects;
    public ArrayList<DynamicEntity> dynamicEntities = new ArrayList<>();
    public SpriteBatch batch;

    public static synchronized BombFactory getInstance() {
        if (instance == null) {
            instance = new BombFactory();
        }
        return instance;
    }

    private BombFactory() {}

    public void createBomb(Vector2 initialPos, Vector2 destination, float speed) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.position.set(initialPos);
        Body body = world.createBody(bodyDef);
        Bomb bomb = new Bomb(body, batch);

        PolygonShape bombShape = new PolygonShape();
        bombShape.setAsBox((PLANE_WIDTH / 2) * Constants.Game.SCALE,
                (PLANE_HEIGHT / 2) * Constants.Game.SCALE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.shape = bombShape;
        gameObjects.put(bomb.getId(), bomb);

        body.createFixture(fixtureDef).setUserData(new FixtureDefinition(bomb.getId(), "body"));
        body.setLinearVelocity(getVelocity(initialPos, destination, speed));

        bombShape.dispose();
        dynamicEntities.add(bomb);
    }

    public Vector2 getVelocity(Vector2 initial, Vector2 destination, float speed) {
        return new Vector2((destination.x - initial.x) * speed, (destination.y - initial.y) * speed);
    }

}
