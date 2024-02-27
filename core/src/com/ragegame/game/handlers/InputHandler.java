package com.ragegame.game.handlers;

import static java.lang.Math.min;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.ragegame.game.objects.PlayerModel;

public class InputHandler implements InputProcessor {
    PlayerModel playerModel;
    long chargeStartTime;
    public InputHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            playerModel.setForce(new Vector2(15, 0));
        } else if (keycode == Input.Keys.A) {
            playerModel.setForce(new Vector2(-15, 0));
        } else if (keycode == Input.Keys.SPACE) {
            chargeStartTime = System.currentTimeMillis();
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D) {
            playerModel.setForce(new Vector2(0, 0));
        } else if (keycode == Input.Keys.A) {
            playerModel.setForce(new Vector2(0, 0));
        } else if (keycode == Input.Keys.SPACE) {
            playerModel.getBody().applyLinearImpulse(new Vector2(0, min(8f, System.currentTimeMillis() - chargeStartTime) * 1f),
                    playerModel.getBody().getPosition(), true);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
