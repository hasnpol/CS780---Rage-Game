package com.ragegame.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.ragegame.game.objects.DynamicEntity.Bomb;
import com.ragegame.game.objects.DynamicEntity.Enemies.Plane;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.DynamicEntity.Bullet;
import com.ragegame.game.objects.StaticEntity.FakePlatform;
import com.ragegame.game.objects.StaticEntity.HiddenPlatform;
import com.ragegame.game.utils.FixtureDefinition;

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
        String fixtureTypeA = "";
        String fixtureTypeB = "";

        if (fixtureA.getUserData() != null && fixtureA.getUserData() instanceof FixtureDefinition) {
            FixtureDefinition def = (FixtureDefinition) fixtureA.getUserData();
            UUID objId = def.id;
            fixtureTypeA = def.fixtureType;
            objA = gameObjects.get(objId);
        }

        if (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof FixtureDefinition) {
            FixtureDefinition def = (FixtureDefinition) fixtureB.getUserData();
            UUID objId = def.id;
            fixtureTypeB = def.fixtureType;
            objB = gameObjects.get(objId);
        }

        if (objB instanceof PlayerModel || objA instanceof PlayerModel) {
            if (objA instanceof PlayerModel) {
                handlePlayerContact((PlayerModel) objA, objB, fixtureTypeA, fixtureTypeB, true);
            } else {
                handlePlayerContact((PlayerModel) objB, objA, fixtureTypeB, fixtureTypeA, true);
            }
        }

        if (objB instanceof Bullet || objA instanceof Bullet) {
            Bullet bullet = (Bullet) ((objA instanceof Bullet)? objA: objB);
            bullet.markedForDelete = true;
        }

        if (objA instanceof Bomb && !(objB instanceof Plane) || objB instanceof Bomb && !(objA instanceof Plane)) {
            Bomb bomb = (Bomb) ((objA instanceof Bomb)? objA: objB);
            bomb.markedForDelete = true;
        }

        if (objA instanceof FakePlatform && objB instanceof PlayerModel) {
            FakePlatform fakePlatform = (FakePlatform) objA;
            fakePlatform.rugPull();
        }

        if (objB instanceof FakePlatform && objA instanceof PlayerModel) {
            FakePlatform fakePlatform = (FakePlatform) objB;
            fakePlatform.rugPull();
        }

        if (objA instanceof HiddenPlatform && objB instanceof PlayerModel) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objA;
            hiddenPlatform.reveal();
        }

        if (objB instanceof HiddenPlatform && objA instanceof PlayerModel) {
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
        String fixtureTypeA = "";
        String fixtureTypeB = "";

        if (fixtureA.getUserData() != null && fixtureA.getUserData() instanceof FixtureDefinition) {
            FixtureDefinition def = (FixtureDefinition) fixtureA.getUserData();
            UUID objId = def.id;
            fixtureTypeA = def.fixtureType;
            objA = gameObjects.get(objId);
        }

        if (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof FixtureDefinition) {
            FixtureDefinition def = (FixtureDefinition) fixtureB.getUserData();
            UUID objId = def.id;
            fixtureTypeB = def.fixtureType;
            objB = gameObjects.get(objId);
        }

        if (objB instanceof PlayerModel || objA instanceof PlayerModel) {
            if (objA instanceof PlayerModel) {
                handlePlayerContact((PlayerModel) objA, objB, fixtureTypeA, fixtureTypeB, false);
            } else {
                handlePlayerContact((PlayerModel) objB, objA, fixtureTypeB, fixtureTypeA, false);
            }
        }

        if (objA instanceof HiddenPlatform && objB instanceof PlayerModel) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objA;
            hiddenPlatform.hide();
        }

        if (objB instanceof HiddenPlatform && objA instanceof PlayerModel) {
            HiddenPlatform hiddenPlatform = (HiddenPlatform) objB;
            hiddenPlatform.hide();
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    private void handlePlayerContact(PlayerModel playerModel, Entity obj, String playerFixture,
                                     String entityFixture, boolean isStartContact) {
        playerModel.playerContactHandler.playerFixtureType = playerFixture;
        playerModel.playerContactHandler.entityFixtureType = entityFixture;
        if (isStartContact) {
            playerModel.playerContactHandler.startContact(obj);
        } else {
            playerModel.playerContactHandler.endContact(obj);
        }
    }
}
