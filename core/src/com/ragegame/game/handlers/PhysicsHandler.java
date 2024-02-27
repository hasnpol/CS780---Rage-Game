package com.ragegame.game.handlers;

import static java.lang.Math.min;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.GameObject;
import com.ragegame.game.objects.actors.Actors;

import java.util.UUID;

public class PhysicsHandler {
    public static final float TIME_STEP = 1/60F;
    World world;
    ObjectMap<UUID, Actors> gameObjects;
    private float accumulator = 0;

    public PhysicsHandler(World world, ObjectMap<UUID, Actors> gameObjects) {
        this.world = world;
        this.gameObjects = gameObjects;
    }
    public void applyForces() {
        for (ObjectMap.Entry<UUID, Actors> b : this.gameObjects) {
            b.value.applyForces();
        }
    }

    public void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }
}
