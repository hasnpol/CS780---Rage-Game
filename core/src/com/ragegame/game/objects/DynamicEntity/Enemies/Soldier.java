package com.ragegame.game.objects.DynamicEntity.Enemies;

import com.badlogic.gdx.physics.box2d.Body;
import com.ragegame.game.objects.DynamicEntity.Enemy;

import static com.ragegame.game.utils.Constants.EnemyConstants.SOLDIER;
public class Soldier extends Enemy {
    public Soldier(Body body) {
        super(body, SOLDIER);
    }
}
