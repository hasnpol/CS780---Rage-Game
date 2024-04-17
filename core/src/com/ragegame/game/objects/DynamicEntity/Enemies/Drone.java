package com.ragegame.game.objects.DynamicEntity.Enemies;

import static com.ragegame.game.utils.Constants.DroneConstants.*;
import static com.ragegame.game.utils.Constants.EntityType.DRONE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.FixtureDefinition;


public class Drone extends EnemyModel {
    private float elapsedTime = 0; // Time elapsed since the start of the movement

    public Drone(Body body, SpriteBatch batch) {
        super(body, batch, DRONE);
        this.getBody().setGravityScale(0);
        enemyBox.setAsBox(DRONE_WIDTH, DRONE_HEIGHT); // 0.25f, 0.25f
        entityFixture.density = DRONE_DENSITY;
        entityFixture.friction = 0;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(.15f, .05f, new Vector2(0, .25f), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));

    }

    @Override
    public void update(SpriteBatch batch) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        PlayerModel player = PlayerModel.getPlayerModel();
        Vector2 playerPosition = new Vector2(player.getBody().getPosition());
        Vector2 dronePosition = new Vector2(this.getBody().getPosition());
        elapsedTime += deltaTime;

//        float sinusoidalOffset = MathUtils.sin(elapsedTime * DRONE_FREQUENCY) * DRONE_AMPLITUDE;

        // Applied the sinusoidal up and down "bounce" movement
        float sinusoidalVelocity = DRONE_AMPLITUDE * MathUtils.sin(MathUtils.PI2 * DRONE_FREQUENCY * elapsedTime);
        getBody().applyForceToCenter(0, sinusoidalVelocity, true);


        Vector2 currentVelocity = getBody().getLinearVelocity();
        getBody().setLinearVelocity(currentVelocity.x, sinusoidalVelocity);

//        float angle = MathUtils.atan2(playerPosition.y - dronePosition.y, playerPosition.x - dronePosition.x);
        Vector2 pursuitMovement = new Vector2(MathUtils.cos(0) * DRONE_SPEED, MathUtils.sin(0) * DRONE_SPEED);
//        Vector2 combinedMovement = pursuitMovement.add(0, sinusoidalOffset);
        dronePosition.add(pursuitMovement);
        getBody().setTransform(dronePosition, 0); // sets the angle, could be used for a gun?

//        float angle = MathUtils.atan2(playerPosition.y - dronePosition.y, playerPosition.x - dronePosition.x);
//
//        // Calculate the direct pursuit movement
//        Vector2 pursuitMovement = new Vector2(MathUtils.cos(angle) * DRONE_SPEED, MathUtils.sin(angle) * DRONE_SPEED);
//
//        // Calculate the sinusoidal movement
//
//
//        // Combine the movements
//        Vector2 combinedMovement = pursuitMovement.add(0, sinusoidalOffset);
//        // Update the drone's position
//        dronePosition.add(combinedMovement);
//
//        System.out.println("Offset: " + sinusoidalOffset);
//
//        getBody().applyForceToCenter(0, sinusoidalOffset, true);
    }

    // Method to calculate the seek behavior towards the player
    private Vector2 seek(Vector2 target, float deltaTime) {
        Vector2 desired = target.sub(getBody().getPosition());
        desired.setLength(DRONE_SPEED * deltaTime);
        return desired;
    }
}
