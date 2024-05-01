package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.view.View;
import com.ragegame.game.utils.Constants.*;

public class DynamicEntity extends Entity {
    public FixtureDef entityFixture;
    private Direction direction = Direction.RIGHT;
    public Object objectBox;

    private final View view;

    public DynamicEntity(Body body, SpriteBatch batch, EntityType type) {
        super(body, type);
        this.getBody().setFixedRotation(true);
        this.entityFixture = new FixtureDef();
        this.view = new View(this, batch);
    }

    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void render(float dt) {
        view.render(dt);
    }

    public void dispose() {
        view.dispose();
    }


    public void update(SpriteBatch batch) {

    }

    public void draw(SpriteBatch batch, TextureRegion currentAnimationFrame,
                     float x_position, float y_position) {}

}
