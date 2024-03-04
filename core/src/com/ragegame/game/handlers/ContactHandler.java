package com.ragegame.game.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.StaticEntity.Platform;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;

import java.util.UUID;

public class ContactHandler implements ContactListener {

    World world;
    ObjectMap<UUID, Entity> gameObjects;
    public ContactHandler(World world, ObjectMap<UUID, Entity> objectMap) {
        this.world = world;
        this.gameObjects = objectMap;
    }



    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Entity objA = null;
        Entity objB = null;

        if (fixtureA.getUserData() != null && fixtureA.getUserData() instanceof UUID) {
            // destroy the cannon ball on collision'
            UUID objId = (UUID) fixtureA.getUserData();
            objA = gameObjects.get(objId);
        }

        if (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof UUID) {
            // destroy the cannon ball on collision'
            UUID objId = (UUID) fixtureB.getUserData();
            objB = gameObjects.get(objId);
        }


        if (objB instanceof PlayerModel || objA instanceof PlayerModel) {
            if (objB instanceof Platform) {
                PlayerModel playerModel = (PlayerModel) objA;
                if(playerModel.getBody().getPosition().y > objB.getBody().getPosition().y) {
                    playerModel.setGrounded(true);
                }
            } else if (objA instanceof  Platform) {
                PlayerModel playerModel = (PlayerModel) objB;
                if(playerModel.getBody().getPosition().y > objA.getBody().getPosition().y) {
                    playerModel.setGrounded(true);
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity objA = null;
        Entity objB = null;

        if (fixtureA.getUserData() != null && fixtureA.getUserData() instanceof UUID) {
            // destroy the cannon ball on collision'
            UUID objId = (UUID) fixtureA.getUserData();
            objA = gameObjects.get(objId);
        }

        if (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof UUID) {
            // destroy the cannon ball on collision'
            UUID objId = (UUID) fixtureB.getUserData();
            objB = gameObjects.get(objId);
        }

        if (objB instanceof PlayerModel || objA instanceof PlayerModel) {
            if (objB instanceof Platform) {
                PlayerModel playerModel = (PlayerModel) objA;
                playerModel.setGrounded(false);
            } else if (objA instanceof Platform) {
                PlayerModel playerModel = (PlayerModel) objB;
                playerModel.setGrounded(false);
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
