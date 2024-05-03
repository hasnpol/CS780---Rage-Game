package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.ragegame.game.factory.BombFactory;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.FixtureDefinition;

import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.PLANE;

public class Plane extends Enemy {
    private float elapsedTime = 0; // Time elapsed since the start of the movement
    long nextShot;

    public Plane(Body body, SpriteBatch batch) {
        super(body, batch, PLANE);
        this.getBody().setGravityScale(0);
        enemyBox.setAsBox(PLANE_WIDTH * Game.SCALE, PLANE_HEIGHT * Game.SCALE);
        entityFixture.density = PLANE_DENSITY;
        entityFixture.friction = 0;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(PLANE_WIDTH * Game.SCALE, .05f,
                new Vector2(0, (PLANE_HEIGHT + .05f) * Game.SCALE), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));
    }

    @Override
    public void update(SpriteBatch batch) {
        if (isDead()) return;
        float deltaTime = Gdx.graphics.getDeltaTime();
        PlayerModel player = PlayerModel.getPlayerModel();
        Vector2 playerPosition = new Vector2(player.getBody().getPosition());
        Vector2 dronePosition = new Vector2(this.getBody().getPosition());
        elapsedTime += deltaTime;

        // Applied the sinusoidal up and down "bounce" movement
        float sinusoidalVelocity = PLANE_AMPLITUDE * MathUtils.sin(MathUtils.PI2 * PLANE_FREQUENCY * elapsedTime);

        Vector2 currentVelocity = getBody().getLinearVelocity();
        getBody().setLinearVelocity(currentVelocity.x, sinusoidalVelocity);

        float angle = MathUtils.atan2(playerPosition.y - dronePosition.y, playerPosition.x - dronePosition.x);
        Vector2 pursuitMovement = new Vector2(MathUtils.cos(angle) * PLANE_SPEED, 0);
        dronePosition.add(pursuitMovement);
        getBody().setTransform(dronePosition, 0); // sets the angle, could be used for a gun?

        this.setDirection((pursuitMovement.x > 0)? Direction.LEFT : Direction.RIGHT);

        playerDirection = isPlayerInRange(PLANE_HORIZONTAL_SIGHT*2, PLANE_VERTICAL_SIGHT, dronePosition);
        if (playerDirection != Direction.STOP) {
            dropBomb(playerPosition);
        }
    }

    public void dropBomb(Vector2 playerPosition) {
        float offset = ((playerDirection == Direction.LEFT)) ? -0.5f : 0.5f;
        long currentTime = System.currentTimeMillis();
        Vector2 initalPosition = getPosition().add(offset, - .5f);
        if (currentTime > nextShot) {
            float xPush = initalPosition.x + ((playerDirection == Direction.LEFT)? -1: 1);
            BombFactory.getInstance().createBomb(initalPosition, new Vector2(xPush, playerPosition.y));
            nextShot = currentTime + PLANE_BOMB_RATE;
        }
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion curAnimationFrame,
                     float x_position, float y_position) {
        batch.draw(curAnimationFrame, x_position-Game.SCALE, y_position-(Game.SCALE/2),
                Game.SCALE*2, Game.SCALE);
    }
}
