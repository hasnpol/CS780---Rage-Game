package com.ragegame.game.objects.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.HelpMethods;

import java.text.BreakIterator;
import java.util.Arrays;

public class View {
    private final DynamicEntity model;
    private final SpriteBatch batch;
    private final Array<Array<Sprite>> animationFrames;
    TextureAtlas textureAtlas;
    protected Animation currentAnimation;
    protected int currentAnimationSequence = -1;
    protected TextureRegion currentAnimationFrame;
    private float stateTime = 0f;

    final float animationFrameDuration= 0.5F;

    public View(DynamicEntity model, SpriteBatch batch) {
        this.model = model;
        this.batch = batch;
        String[] texturePaths = HelpMethods.GetTextureAtlas(model.type);
        assert texturePaths != null;
        this.textureAtlas = new TextureAtlas(texturePaths[0]);
        this.animationFrames = new Array<>();
        String[] textures = Arrays.copyOfRange(texturePaths, 1, texturePaths.length);
        for (String texture : textures) { // Gets array excluding texturePath
            animationFrames.add(textureAtlas.createSprites(texture));
        }
        currentAnimation = new Animation<>(this.animationFrameDuration, animationFrames.get(0));
        stateTime = 0f;
    }

    public void render(float dt) {
        if (model instanceof PlayerModel) {
            PlayerModel playerModel;
            playerModel = (PlayerModel) model;
            if (playerModel.isDead()) {
                return;
            }
        }

        if (model instanceof EnemyModel) {
            EnemyModel enemyModel;
            enemyModel = (EnemyModel) model;
            if (enemyModel.isDead()) {
                return;
            }
        }

        int nextAnimationSequence = getAnimationSequenceFromMovementDirection();
        if (currentAnimationSequence != nextAnimationSequence) {
            Array<Sprite> spriteList = animationFrames.get(nextAnimationSequence);
            currentAnimation = new Animation<>(this.animationFrameDuration, spriteList);
            currentAnimationSequence = nextAnimationSequence;
        }

        stateTime += dt;
        currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentAnimationFrame, this.model.getBody().getPosition().x - (float)1/2,
                this.model.getBody().getPosition().y- (float)1/2, 1, 1);
    }

    public int getAnimationSequenceFromMovementDirection() {
        if (this.model.getMovementVector().x < 0) {
            return 2 - ((this.model.getMovementVector().y > 0)? 1:0);
        } else if (this.model.getMovementVector().x > 0) {
            return 5 - ((this.model.getMovementVector().y > 0)? 1:0);
        }
        // Can expect this.model.getMovementVector().x == 0
        if (this.model.type == Constants.EntityType.ENEMY) {
            EnemyModel temp = (EnemyModel) this.model;
            return (temp.getDirection() == Constants.Direction.LEFT)? 3:0;
        } else if (this.model.type == Constants.EntityType.PLAYER) {
            PlayerModel temp = (PlayerModel) this.model;
            return (temp.getDirection() == Constants.Direction.LEFT)? 3:0;
        }
        return 0;
    }

    public void dispose() {
        this.textureAtlas.dispose();
    }
}
