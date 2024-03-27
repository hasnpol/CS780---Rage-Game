package com.ragegame.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.StaticEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.FakePlatform;
import com.ragegame.game.objects.StaticEntity.HiddenPlatform;

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

        if (objB instanceof PlayerModel) {
            PlayerModel playerModel = (PlayerModel) objB;
            playerModel.playerContactHandler.startContact(objA);
        }

        if (objA instanceof PlayerModel) {
            PlayerModel playerModel = (PlayerModel) objA;
            playerModel.playerContactHandler.startContact(objB);
        }

        if (objB instanceof Bullet) {
            Bullet bullet = (Bullet) objB;
            bullet.markedForDelete = true;
        }

        if (objA instanceof Bullet) {
            Bullet bullet = (Bullet) objA;
            bullet.markedForDelete = true;
        }

        if (objA instanceof FakePlatform) {
            FakePlatform fakePlatform = (FakePlatform) objA;
            fakePlatform.rugPull();
        }

        if (objB instanceof FakePlatform) {
            FakePlatform fakePlatform = (FakePlatform) objB;
            fakePlatform.rugPull();
        }

        if (objA instanceof HiddenPlatform) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objA;
            hiddenPlatform.reveal();
        }

        if (objB instanceof HiddenPlatform) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objB;
            hiddenPlatform.reveal();
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

        if (objB instanceof PlayerModel) {
            PlayerModel playerModel = (PlayerModel) objB;
            playerModel.playerContactHandler.endContact(objA, world);
        }

        if (objA instanceof PlayerModel) {
            PlayerModel playerModel = (PlayerModel) objA;
            playerModel.playerContactHandler.endContact(objB, world);
        }

        if (objA instanceof HiddenPlatform) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objA;
            hiddenPlatform.hide();
        }

        if (objB instanceof HiddenPlatform) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objB;
            hiddenPlatform.hide();
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
