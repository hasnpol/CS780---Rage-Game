package com.ragegame.game.objects.StaticEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;

public class Goal extends StaticEntity {

    public Goal(Body body) {
        super(body, Constants.EntityType.GOAL);
    }

}
