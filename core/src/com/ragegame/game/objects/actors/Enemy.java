package com.ragegame.game.objects.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import static com.ragegame.game.utils.Constants.EntityType.*;

import static com.ragegame.game.utils.Constants.EnemyConstants.*;

// https://www.youtube.com/watch?v=N7zib3qm5Oc&t=264s


public abstract class Enemy extends Actors {
    public Body b2body;
    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed = 25;
    public Enemy(Body body, int enemyType) {
        super(body, ENEMY);
        this.enemyType = enemyType;
        Vector2 curPos = body.getPosition();
        initHitbox(curPos.x, curPos.y, 50, 50); // FIXME later!
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, aniIndex)) {
                aniIndex = 0;
            }
        }
    }

    public void update() {
        updateAnimationTick();
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
