package com.ragegame.game.objects.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;


import static com.ragegame.game.utils.Constants.EnemyConstants.RUSSIAN;
public class Russian extends Enemy {
    public Russian(Body body) {
        super(body, RUSSIAN);
    }
}
