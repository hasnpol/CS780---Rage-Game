package com.ragegame.game.objects.DynamicEntity;

import static com.ragegame.game.utils.Constants.EntityType.RESOURCE;
import static com.ragegame.game.utils.Constants.ResourceConstants.ResType.MEDAL;

import com.badlogic.gdx.physics.box2d.Body;

public class Medal extends Collectable {
    public Medal(Body body) {
        super(body, RESOURCE.SubType(MEDAL));
    }
}
