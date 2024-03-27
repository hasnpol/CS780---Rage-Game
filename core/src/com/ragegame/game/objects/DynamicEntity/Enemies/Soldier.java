package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import static com.ragegame.game.utils.Constants.EnemyType.SOLDIER;

public class Soldier extends EnemyModel {
    public Soldier(Body body) {
        super(body, SOLDIER);
    }
}
