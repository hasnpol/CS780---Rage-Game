package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.view.View;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.Constants.*;

public class DynamicEntity extends Entity {
    public FixtureDef entityFixture;
    private Direction direction = Direction.RIGHT;

    private View view;

    public DynamicEntity(Body body, SpriteBatch batch, EntityType type) {
        super(body, type);
        this.getBody().setFixedRotation(true);
        this.entityFixture = new FixtureDef();
        View enemyView = new View(this, batch);
        this.setView(enemyView);
    }

    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setView(View view) {
        this.view = view;
    }

    public void render(float dt) {
        view.render(dt);
    }

    public void dispose() {
        view.dispose();
    }

    public void update(SpriteBatch batch) {}

}
