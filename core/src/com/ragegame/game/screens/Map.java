package com.ragegame.game.screens;

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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.actors.Actors;
import com.ragegame.game.objects.actors.Platform;

import java.util.UUID;

public class Map {

    public TiledMap map;
    private World world;
    private float width;
    private float height;
    public float PPM = 128f;

    public ObjectMap<UUID, Actors> gameObjects;

    public Map(World world, ObjectMap<UUID, Actors> gameObjects) {
        this.world = world;
        this.gameObjects = gameObjects;
    }

    public OrthogonalTiledMapRenderer buildMap() {

        map = new TmxMapLoader().load("maps/desert/gameart2d-desert.tmx");

        createMapLayers(map.getLayers());

        MapProperties properties = map.getProperties();
        this.width = properties.get("width", Integer.class);
        this.height = properties.get("height", Integer.class);

        return new OrthogonalTiledMapRenderer(map, 1/PPM);
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
                System.out.println("Today is Saturday");
                break;
            case "platform":
                createPlatform((PolygonMapObject) mapObject);
                break;
            default:
                break;
        }
    }

    private void createPlatform(PolygonMapObject polygonMapObject) {

        Shape shape = createPolygonShape(polygonMapObject);
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        //bodyDef.position = polygonMapObject.getPolygon().getVertex(1, new Vector2());
        Body body = world.createBody(bodyDef);

        Platform platform = new Platform(body);

        body.createFixture(shape, 10000).setUserData(platform.getId());

        gameObjects.put(platform.getId(), platform);
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getPPM() {return this.PPM; }

}
