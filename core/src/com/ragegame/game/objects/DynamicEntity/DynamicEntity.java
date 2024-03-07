package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.view.View;
import com.ragegame.game.utils.Constants;

public class DynamicEntity extends Entity {

    private View view;

    public DynamicEntity(Body body, Constants.EntityType type) {
        super(body, type);
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

}
