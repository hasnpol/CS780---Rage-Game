package com.ragegame.game.objects.view;

import static com.ragegame.game.utils.Constants.*;
import static com.ragegame.game.utils.Constants.EnemyConstants.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ragegame.game.objects.DynamicEntity.*;
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
    final float animationFrameDuration = 0.5F;

    public View(DynamicEntity model, SpriteBatch batch) {
        this.model = model;
        this.batch = batch;
        UtilTypes sprite_textures = HelpMethods.GetTextureAtlas(model.type);
        assert sprite_textures != null;
        this.textureAtlas = new TextureAtlas(sprite_textures.resPath);
        for (String texture : sprite_textures.animations) {
            animationFrames.add(textureAtlas.createSprites(texture));
        }
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

        if (model instanceof Bomb && model.isAttacking()) {
            Bomb bomb;
            bomb = (Bomb) model;
            if ((System.currentTimeMillis() - bomb.attackTime) > 200)
            {
                bomb.markedForDelete = true;
            }
        }

        // FIXME when this is removed, and a coin is collected, enemies will fire coins?????
        if (model instanceof Coin) {
            Coin coin;
            coin = (Coin) model;
            if (coin.isCollected) {return;}
        }

        // TODO ADD LOGIC TO RENDER DEATH ANIMATION AND THEN HAVE PLAYER DISAPPEAR
        int nextAnimationSequence = getAnimationSequenceFromMovementDirection();
        if (currentAnimationSequence != nextAnimationSequence) {
            Array<Sprite> spriteList = animationFrames.get(nextAnimationSequence);
            currentAnimation = new Animation<>(this.animationFrameDuration, spriteList);
            currentAnimationSequence = nextAnimationSequence;
        }

        stateTime += dt;
        if (model instanceof Bomb && model.isAttacking()) {
            int explosion_time = (int) (stateTime / BOMB_EXPLODE_ANIMATION) % 24;
            currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(explosion_time, true);
        } else {
            currentAnimationFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
            if (currentAnimationFrame.isFlipX() != shouldFlip) {// Flip horizontally without flipping vertically
                currentAnimationFrame.flip(true, false);
            }
        }
        float x_position = this.model.getBody().getPosition().x;
        float y_position = this.model.getBody().getPosition().y;
        model.draw(batch, currentAnimationFrame, x_position, y_position, 1.0f);
    }

    public int getAnimationSequenceFromMovementDirection() {
        if (this.model.type == EntityType.RESOURCE) return 0; // Collectables will probably have only one animation?

        // TODO ============================
        //      Figure out a way to render death animation before disappearing
        //      & add ATTACKING animation
        return model.state.ordinal();
    }

    public void dispose() {
        this.textureAtlas.dispose();
        if (this.clothes_textureAtlas != null) {
            this.clothes_textureAtlas.dispose();
        }
    }
}
