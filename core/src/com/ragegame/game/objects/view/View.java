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
import com.ragegame.game.utils.Constants.State;
import com.ragegame.game.utils.HelpMethods;
import com.ragegame.game.utils.LoadSave;

import java.text.BreakIterator;
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
        if (model.type != null && model.type.getType() == Constants.EnemyType.BOAR) {
            int o = 0;
        }
        LoadSave.SPRITE sprite_textures = HelpMethods.GetTextureAtlas(model.type);
        assert sprite_textures != null;
        this.textureAtlas = new TextureAtlas(sprite_textures.resPath);
        for (String texture : sprite_textures.flattenMap()) { // Gets array excluding texturePath
            animationFrames.add(textureAtlas.createSprites(texture));
        }
        currentAnimation = new Animation<>(this.animationFrameDuration, animationFrames.get(0));
        stateTime = 0f;
    }

    public void render(float dt) {
        boolean isDead = (model instanceof PlayerModel && ((PlayerModel) model).isDead()) ||
                (model instanceof EnemyModel && ((EnemyModel) model).isDead());
        // TODO ADD LOGIC TO RENDER DEATH ANIMATION AND THEN HAVE PLAYER DISAPPEAR
        int nextAnimationSequence = getAnimationSequenceFromMovementDirection(isDead);
        if (currentAnimationSequence != nextAnimationSequence) {
            if (this.model.type != null && this.model.type.getType() == Constants.EnemyType.BOAR) {
                int x = 0;
            }
            Array<Sprite> spriteList = animationFrames.get(nextAnimationSequence);
            currentAnimation = new Animation<>(this.animationFrameDuration, spriteList);
            currentAnimationSequence = nextAnimationSequence;
        }

        stateTime += dt;
        currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentAnimationFrame, this.model.getBody().getPosition().x - (float)1/2,
                this.model.getBody().getPosition().y- (float)1/2, 1, 1);


    }

    public int getAnimationSequenceFromMovementDirection(boolean isDead) {
        if (isDead) {
            return (State.DEAD.ordinal() * 2 + model.getDirection().getNum());
        } else if (this.model.getMovementVector().y != 0) { // If JUMPING
            return (State.JUMPING.ordinal() * 2 + model.getDirection().getNum());
        } else if (this.model.getMovementVector().x != 0) { // If RUNNING
            return (State.RUNNING.ordinal() * 2 + model.getDirection().getNum());
        } else { // otherwise Idle
            return (State.IDLE.ordinal() * 2 + model.getDirection().getNum());
        }
    }

    public void dispose() {
        this.textureAtlas.dispose();
    }
}
