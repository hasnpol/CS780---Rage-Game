package com.ragegame.game.objects.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.Constants.State;
import com.ragegame.game.utils.Constants.Direction;
import com.ragegame.game.utils.Constants.EntityType;
import com.ragegame.game.utils.HelpMethods;
import com.ragegame.game.utils.LoadSave;
import com.ragegame.game.utils.UtilTypes;
import com.ragegame.game.utils.UtilTypes.*;

import java.util.Arrays;

public class View {
    private final DynamicEntity model;
    private final SpriteBatch batch;
    private final Array<Array<Sprite>> animationFrames = new Array<>();
    TextureAtlas textureAtlas;
    protected Animation currentAnimation;
    protected int currentAnimationSequence = -1;
    protected TextureRegion currentAnimationFrame;
    private float stateTime = 0f;

    final float animationFrameDuration= 0.5F;

    public View(DynamicEntity model, SpriteBatch batch) {
        this.model = model;
        this.batch = batch;
        if (model.type == EntityType.RESOURCE) {
            int x = 0;
        }
        UtilTypes sprite_textures = HelpMethods.GetTextureAtlas(model.type);
        assert sprite_textures != null;
        this.textureAtlas = new TextureAtlas(sprite_textures.resPath);
        for (String texture : sprite_textures.animations) { // Gets array excluding texturePath
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
            if (enemyModel.isDead) {
                return;
            }
        }

        if (model instanceof Coin) {
            Coin coin;
            coin = (Coin) model;
            if (coin.isCollected) {
                return;
            }
        }

        // TODO ============================
        // TODO Figure out a way to render death animation before disappearing
        boolean isDead = (model instanceof PlayerModel && ((PlayerModel) model).isDead()) ||
                (model instanceof EnemyModel && ((EnemyModel) model).isDead);
        // TODO ============================

        // TODO ADD LOGIC TO RENDER DEATH ANIMATION AND THEN HAVE PLAYER DISAPPEAR
        int nextAnimationSequence = getAnimationSequenceFromMovementDirection(isDead);
        if (currentAnimationSequence != nextAnimationSequence) {
            Array<Sprite> spriteList = animationFrames.get(nextAnimationSequence);
            currentAnimation = new Animation<>(this.animationFrameDuration, spriteList);
            currentAnimationSequence = nextAnimationSequence;
        }

        stateTime += dt;
        currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
        boolean shouldFlip = model.getDirection().getNum() == 1; // Assuming 1 indicates left direction, adjust based on your implementation
        if (currentAnimationFrame.isFlipX() != shouldFlip) {
            currentAnimationFrame.flip(true, false); // Flip horizontally without flipping vertically
        }
        if (model instanceof Coin) {
            batch.draw(currentAnimationFrame, this.model.getBody().getPosition().x - .25f,
                    this.model.getBody().getPosition().y - .25f, (float) 1/2, (float) 1/2);
        } else {
            batch.draw(currentAnimationFrame, this.model.getBody().getPosition().x - (float) 1 / 2,
                    this.model.getBody().getPosition().y - (float) 1 / 2, 1, 1);
        }
    }

    public int getAnimationSequenceFromMovementDirection(boolean isDead) {
        if (this.model.type == EntityType.RESOURCE) {
            return 0; // Collectables will probably have only one animation?
        } else {
            if (isDead) {
                return State.DEAD.ordinal();
            } else if (this.model.getMovementVector().y != 0) { // If JUMPING
                return State.JUMPING.ordinal();
            } else if (this.model.getMovementVector().x != 0) { // If RUNNING
                return State.RUNNING.ordinal();
            } else { // otherwise Idle
                return State.IDLE.ordinal();
            }
        }
    }

    public void dispose() {
        this.textureAtlas.dispose();
    }
}
