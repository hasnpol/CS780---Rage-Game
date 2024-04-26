package com.ragegame.game.objects.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ragegame.game.objects.DynamicEntity.Bomb;
import com.ragegame.game.objects.DynamicEntity.Bullet;
import com.ragegame.game.objects.DynamicEntity.Coin;
import com.ragegame.game.objects.DynamicEntity.Enemies.*;
import com.ragegame.game.objects.DynamicEntity.Enemy;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.DynamicEntity.DynamicEntity;
import com.ragegame.game.utils.Constants.State;
import com.ragegame.game.utils.Constants.EntityType;
import com.ragegame.game.utils.HelpMethods;
import com.ragegame.game.utils.UtilTypes;

public class View {
    private final DynamicEntity model;
    private final SpriteBatch batch;
    private final Array<Array<Sprite>> animationFrames = new Array<>();
    private final Array<Array<Sprite>> clothes_animationFrames = new Array<>();
    TextureAtlas textureAtlas;
    TextureAtlas clothes_textureAtlas;
    protected Animation currentAnimation;
    protected Animation clothes_currentAnimation;
    protected int currentAnimationSequence = -1;
    protected TextureRegion currentAnimationFrame;
    protected TextureRegion clothes_currentAnimationFrame;
    private float stateTime = 0f;
    final float animationFrameDuration= 0.5F;

    public View(DynamicEntity model, SpriteBatch batch) {
        this.model = model;
        System.out.println("Type " + this.model);
        this.batch = batch;
        UtilTypes sprite_textures = HelpMethods.GetTextureAtlas(model.type);
//        UtilTypes clothes_textures = HelpMethods.GetClothesTextureAtlas();
        assert sprite_textures != null;
        System.out.println("resPath " + sprite_textures.resPath);
        this.textureAtlas = new TextureAtlas(sprite_textures.resPath);
//        this.clothes_textureAtlas = new TextureAtlas(clothes_textures.resPath);
        for (String texture : sprite_textures.animations) {
            System.out.println("texture " + texture);
            animationFrames.add(textureAtlas.createSprites(texture));
        }
        currentAnimation = new Animation<>(this.animationFrameDuration, animationFrames.get(0));
//        for (String texture : clothes_textures.animations) {
//            clothes_animationFrames.add(clothes_textureAtlas.createSprites(texture));
//        }
//        clothes_currentAnimation = new Animation<>(this.animationFrameDuration, clothes_animationFrames.get(0));
        stateTime = 0f;
    }

    public void render(float dt) {
        boolean isPlayerModel = false;
        boolean shouldFlip = model.getDirection().getNum() == 1; // 1 indicates left direction

        if (model instanceof PlayerModel) {
            isPlayerModel = true;
            PlayerModel playerModel;
            playerModel = (PlayerModel) model;
            if (playerModel.isDead()) {return;}
        }

        if (model instanceof Enemy) {
            Enemy enemy;
            enemy = (Enemy) model;
            if (enemy.isDead) {return;}
        }

        // FIXME when this is removed, and a coin is collected, enemies will fire coins?????
        if (model instanceof Coin) {
            Coin coin;
            coin = (Coin) model;
            if (coin.isCollected) {return;}
        }

        // TODO ============================
        // TODO Figure out a way to render death animation before disappearing
        boolean isDead = (model instanceof PlayerModel && ((PlayerModel) model).isDead()) ||
                (model instanceof Enemy && ((Enemy) model).isDead);
        // TODO ============================

        // TODO ADD LOGIC TO RENDER DEATH ANIMATION AND THEN HAVE PLAYER DISAPPEAR
        int nextAnimationSequence = getAnimationSequenceFromMovementDirection(isDead);
        if (currentAnimationSequence != nextAnimationSequence) {
            Array<Sprite> spriteList = animationFrames.get(nextAnimationSequence);
            currentAnimation = new Animation<>(this.animationFrameDuration, spriteList);
//            Array<Sprite> clothes_spriteList = clothes_animationFrames.get(nextAnimationSequence);
//            clothes_currentAnimation = new Animation<>(this.animationFrameDuration, clothes_spriteList);
            currentAnimationSequence = nextAnimationSequence;
        }

        if (model instanceof Bullet) {
            int x = 0;
        }
        stateTime += dt;
        currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
//        clothes_currentAnimationFrame = (TextureRegion) clothes_currentAnimation.getKeyFrame(stateTime, true);
        if (currentAnimationFrame.isFlipX() != shouldFlip) {// Flip horizontally without flipping vertically
            currentAnimationFrame.flip(true, false);
//            clothes_currentAnimationFrame.flip(true, false);
        }
        float x_position = this.model.getBody().getPosition().x;
        float y_position = this.model.getBody().getPosition().y;
        model.draw(batch, currentAnimationFrame, x_position, y_position, 1.0f);
//        if (isPlayerModel) {
//            model.draw(batch, clothes_currentAnimationFrame, x_position-0.1f, y_position-0.4f, 0.5f);
//        }
    }

    public int getAnimationSequenceFromMovementDirection(boolean isDead) {
        if (this.model.type == EntityType.RESOURCE) {
            return 0; // Collectables will probably have only one animation?
        } else {
            // TODO ADD ATTACKING ANIMATION
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
        if (this.clothes_textureAtlas != null) {
            this.clothes_textureAtlas.dispose();
        }
    }
}
