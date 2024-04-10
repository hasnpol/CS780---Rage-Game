package com.ragegame.game.objects.DynamicEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.utils.Constants;
import static com.ragegame.game.utils.Constants.EntityType.*;
import static com.ragegame.game.utils.Constants.ResourceConstants.ResType.COIN;
import com.ragegame.game.utils.UtilTypes;

public class Coin extends Collectable {
    public Coin(Body body) {
        super(body, RESOURCE.SubType(COIN));
    }
}
