package com.ragegame.game.objects.DynamicEntity.Enemies;


import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.FixtureDefinition;


import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.Constants.*;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.EnemyType.DRONE;


public class Drone extends Enemy {

    public Drone(Body body, SpriteBatch batch) {
        super(body, batch, DRONE);
        this.getBody().setGravityScale(0);
        enemyBox.setAsBox(DRONE_WIDTH * Game.SCALE, DRONE_HEIGHT * Game.SCALE); // 0.25f, 0.25f
        entityFixture.density = DRONE_DENSITY;
        entityFixture.friction = 0;
        entityFixture.shape = enemyBox;
        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "body"));

        enemyBox.setAsBox(.25f, .02f, new Vector2(0, .25f), 0);
        entityFixture.shape = enemyBox;

        this.getBody().createFixture(entityFixture).setUserData(new FixtureDefinition(this.getId(), "head"));

    }

    @Override
    public void update(SpriteBatch batch) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        PlayerModel player = PlayerModel.getPlayerModel();
        Vector2 playerPosition = new Vector2(player.getBody().getPosition());
        Vector2 dronePosition = new Vector2(this.getBody().getPosition());

        Vector2 currentVelocity = getBody().getLinearVelocity();
        getBody().setLinearVelocity(currentVelocity);

        Vector2 pursuitMovement = seek(playerPosition, deltaTime);
        dronePosition.add(pursuitMovement);
        getBody().setTransform(dronePosition, 0); // sets the angle, could be used for a gun?
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position, float new_scale) {
        float scale = (Game.SCALE/2);
        batch.draw(currentAnimationFrame, x_position - scale, y_position - scale,
                Game.SCALE, Game.SCALE);
    }

    // Method to calculate the seek behavior towards the player
    private Vector2 seek(Vector2 target, float deltaTime) {
        Vector2 desired = target.sub(getBody().getPosition());
        desired.setLength(DRONE_SPEED * deltaTime);
        return desired;
    }
}
