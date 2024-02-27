package com.ragegame.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.UUID;

public class GameObject {

    /// This class needs more work. I plan to use this as a super class for non-physics objects
    private final Body body;

    private UUID id;

    private Vector2 force;

    public GameObject(Body body) {
        this.body = body;
        this.force = new Vector2();
        this.id = UUID.randomUUID();
    }

}
