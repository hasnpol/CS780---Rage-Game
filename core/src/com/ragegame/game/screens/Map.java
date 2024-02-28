package com.ragegame.game.screens;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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

    public ObjectMap<UUID, Actors> gameObjects;

    public Map(World world, ObjectMap<UUID, Actors> gameObjects) {
        this.world = world;
        this.gameObjects = gameObjects;
    }

    public OrthogonalTiledMapRenderer buildMap() {
        map = new TmxMapLoader().load("maps/map1/map1.tmx");
        buildMapObject(map.getLayers().get("Object Layer 1").getObjects());
        return new OrthogonalTiledMapRenderer(map , 1/32f);
    }

    private void buildMapObject(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 10000);
        Platform platform = new Platform(body);
        gameObjects.put(platform.getId(), platform);
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / 32.0f, vertices[i * 2 + 1] / 32.0f);
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

}
