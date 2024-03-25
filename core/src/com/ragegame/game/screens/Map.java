package com.ragegame.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.FakePlatform;
import com.ragegame.game.objects.StaticEntity.HiddenPlatform;
import com.ragegame.game.objects.StaticEntity.Platform;
import com.ragegame.game.objects.view.View;

import java.util.ArrayList;
import java.util.UUID;

public class Map {

    public TiledMap map;
    private World world;
    private float width;
    private float height;
    public float PPM;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public ObjectMap<UUID, Entity> gameObjects;
    public ArrayList<DynamicEntity> dynamicEntities = new ArrayList<>();
    public PlayerModel playerModel;

    public Map(World world, ObjectMap<UUID, Entity> gameObjects, SpriteBatch batch, OrthographicCamera camera) {

        this.world = world;
        this.gameObjects = gameObjects;
        this.batch = batch;
        this.camera = camera;

        map = new TmxMapLoader().load("maps/level_1/level_1.tmx");

        MapProperties properties = map.getProperties();
        this.width = properties.get("width", Integer.class);
        this.height = properties.get("height", Integer.class);
        this.PPM = (float) properties.get("tilewidth", Integer.class);

        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM);

        createMapLayers(map.getLayers());

    }

    private void createMapLayers(MapLayers layers) {
        for (MapLayer mapLayer : layers) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                createMapObject(mapObject, mapLayer.getName());
            }
        }
    }

    private void createMapObject(MapObject mapObject, String layer) {
        switch (layer) {
            case "player":
                if (mapObject instanceof PolygonMapObject)
                    createPlayerModel((PolygonMapObject) mapObject);
                break;
            case "enemy":
                if (mapObject instanceof PolygonMapObject)
                    createEnemyModel((PolygonMapObject) mapObject);
                break;
            case "platform":
                if (mapObject instanceof PolygonMapObject) {
                    createPlatform((PolygonMapObject) mapObject);
                }
                break;
            case "fake":
                if (mapObject instanceof PolygonMapObject) {
                    createFakePlatform((PolygonMapObject) mapObject);
                }
                break;
            case "hidden":
                if (mapObject instanceof PolygonMapObject) {
                    createHiddenPlatform((PolygonMapObject) mapObject);
                }
                break;
            default:
                break;
        }
    }

    public void createFakePlatform(PolygonMapObject mapObject) {
        MapLayer tiledLayer = map.getLayers().get(mapObject.getName());

        BodyDef bodyDef = new BodyDef();
        Shape shape = createPolygonShape(mapObject, bodyDef);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);


        FakePlatform platform = new FakePlatform(body, mapObject.getPolygon().getX() / PPM,
                mapObject.getPolygon().getY()/ PPM, tiledLayer);
        body.createFixture(shape, 0).setUserData(platform.getId());
        gameObjects.put(platform.getId(), platform);
    }

    public void createHiddenPlatform(PolygonMapObject mapObject) {
        MapLayer tiledLayer = map.getLayers().get(mapObject.getName());

        BodyDef bodyDef = new BodyDef();
        Shape shape = createPolygonShape(mapObject, bodyDef);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        HiddenPlatform platform = new HiddenPlatform(body, mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM, tiledLayer);
        body.createFixture(shape, 0).setUserData(platform.getId());
        gameObjects.put(platform.getId(), platform);
    }

    public void createPlayerModel(PolygonMapObject mapObject) {
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM);

        Body playerBody = world.createBody(playerBodyDef);
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(0.25f, 0.5f);

        this.playerModel = new PlayerModel(playerBody);
        View playerView = new View(playerModel, batch);
        playerModel.setView(playerView);
        gameObjects.put(playerModel.getId(), playerModel);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 2f;  // more density -> bigger mass for the same size
        fixtureDef.friction = 1;
        playerBody.setFixedRotation(true);
        playerBody.createFixture(fixtureDef).setUserData(playerModel.getId());
        playerBox.dispose();
        dynamicEntities.add(playerModel);
    }

    public void createEnemyModel(PolygonMapObject mapObject) {
		BodyDef enemyBodyDef = new BodyDef();
		enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
		enemyBodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM);

		Body enemyBody = world.createBody(enemyBodyDef);
		PolygonShape enemyBox = new PolygonShape();
		enemyBox.setAsBox(0.25f, 0.5f);

		EnemyModel enemyModel = new EnemyModel(enemyBody);
		View enemyView = new View(enemyModel, batch);
        enemyModel.setView(enemyView);

        gameObjects.put(enemyModel.getId(), enemyModel);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = enemyBox;
		fixtureDef.density = 2f;  // more density -> bigger mass for the same size
		fixtureDef.friction = 1;

        enemyBody.setFixedRotation(true);
        enemyBody.createFixture(fixtureDef).setUserData(enemyModel.getId());
        enemyBox.dispose();
        dynamicEntities.add(enemyModel);
    }

    private void createPlatform(PolygonMapObject polygonMapObject) {

        BodyDef bodyDef = new BodyDef();
        Shape shape = createPolygonShape(polygonMapObject, bodyDef);

        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        Platform platform = new Platform(body, polygonMapObject.getPolygon().getX() / PPM, polygonMapObject.getPolygon().getY()/ PPM);

        body.createFixture(shape, 10000).setUserData(platform.getId());

        gameObjects.put(platform.getId(), platform);
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject, BodyDef bodyDef) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    public void renderDynamicEntities(float dt) {
        for (DynamicEntity dynamicEntity : dynamicEntities) {
            dynamicEntity.render(dt);
        }
    }

    public void disposeDynamicEntities() {
        for (DynamicEntity dynamicEntity : dynamicEntities) {
            dynamicEntity.dispose();
        }
    }

    public void render(float dt) {
        this.orthogonalTiledMapRenderer.setView(camera);
        this.orthogonalTiledMapRenderer.render();
        renderDynamicEntities(dt);
    }

    public void dispose() {
        this.orthogonalTiledMapRenderer.dispose();
        disposeDynamicEntities();
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getPPM() { return this.PPM; }
}
