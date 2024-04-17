package com.ragegame.game.map;

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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.Enemies.BoarModel;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.DynamicEntity.Enemies.Drone;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.Enemies.Gunmen;
import com.ragegame.game.objects.DynamicEntity.Medal;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.FakePlatform;
import com.ragegame.game.objects.StaticEntity.HiddenPlatform;
import com.ragegame.game.objects.StaticEntity.Platform;
import com.ragegame.game.objects.view.View;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.*;
import com.ragegame.game.objects.DynamicEntity.*;
import com.ragegame.game.objects.DynamicEntity.Enemies.*;

import java.util.*;

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
                createMapObject((PolygonMapObject) mapObject, mapLayer.getName());
            }
        }
    }

    private void createMapObject(PolygonMapObject mapObject, String layer) {
        ArrayList<String> enemies = new ArrayList<>(Arrays.asList("enemy", "drone", "boar", "plane", "gunmen"));
        ArrayList<String> platforms = new ArrayList<>(Arrays.asList("fake", "platform", "hidden"));
        if (mapObject instanceof PolygonMapObject) {
            if (platforms.contains(layer)) {
                createPlatforms((PolygonMapObject) mapObject, layer);
            } else if (Objects.equals(layer, "player")) {
                createPlayerModel((PolygonMapObject) mapObject);
            } else if (enemies.contains(layer)) {
                createEnemies((PolygonMapObject) mapObject, layer);
            } else if (Objects.equals(layer, "coin")) {
                createCoin((PolygonMapObject) mapObject);
            } else if (Objects.equals(layer, "medal")) {
                createMedal((PolygonMapObject) mapObject);
            }
        }
    }

    private void createEnemies(PolygonMapObject mapObject, String layer) {
        BodyDef enemyBodyDef = new BodyDef();
        enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
        enemyBodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY() / PPM);

        Body enemyBody = world.createBody(enemyBodyDef);
        EnemyModel enemyModel;

        if (Objects.equals(layer, "boar")) {
            enemyModel = new BoarModel(enemyBody, batch);
        } else if (Objects.equals(layer, "drone")) {
            enemyModel = new Drone(enemyBody, batch);
        } else if (Objects.equals(layer, "plane")) {
            enemyModel = new Plane(enemyBody, batch);
        } else if (Objects.equals(layer, "gunmen")) { // Default case is for Gunmen
            enemyModel = new Gunmen(enemyBody, batch);
        } else {
            enemyModel = null;
        }

        gameObjects.put(enemyModel.getId(), enemyModel);
        enemyModel.enemyBox.dispose();
        dynamicEntities.add(enemyModel);
    }

    public void createPlatforms(PolygonMapObject mapObject, String layer) {
        MapLayer tiledLayer;
        BodyDef bodyDef = new BodyDef();
        Shape shape = createPolygonShape(mapObject, bodyDef);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Platform platform;
        switch(layer) {
            case "fake":
                tiledLayer = map.getLayers().get(mapObject.getName());
                platform = new FakePlatform(body, mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM, tiledLayer);
                fixtureDef.isSensor = true;
                break;
            case "hidden":
                tiledLayer = map.getLayers().get(mapObject.getName());
                platform = new HiddenPlatform(body, mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM, tiledLayer);
                fixtureDef.density = 0;
                break;
            default:
                platform = new Platform(body, mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM);
                fixtureDef.density = 10000;
                break;
        }
        body.createFixture(fixtureDef).setUserData(platform.getId());
        gameObjects.put(platform.getId(), platform);
    }

    public void createPlayerModel(PolygonMapObject mapObject) {
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY() / PPM);
        Body playerBody = world.createBody(playerBodyDef);

        this.playerModel = new PlayerModel(playerBody, batch);
        gameObjects.put(playerModel.getId(), playerModel);
        this.playerModel.playerBox.dispose();
        dynamicEntities.add(playerModel);
    }

    private void createCoin(PolygonMapObject mapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM);
        Body coinBody = world.createBody(bodyDef);
        Coin coin = new Coin(coinBody, batch);

        gameObjects.put(coin.getId(), coin);
        coin.collectableCircle.dispose();
        dynamicEntities.add(coin);
    }

    private void createMedal(PolygonMapObject mapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(mapObject.getPolygon().getX() / PPM, mapObject.getPolygon().getY()/ PPM);
        Body coinBody = world.createBody(bodyDef);
        Medal medal = new Medal(coinBody, batch);

        gameObjects.put(medal.getId(), medal);
        medal.collectableCircle.dispose();
        dynamicEntities.add(medal);
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

    public void updateDynamic() {
        for (DynamicEntity dynamicEntity: dynamicEntities) {
            dynamicEntity.update(batch);
        }
    }
}
