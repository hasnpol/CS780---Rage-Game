package com.ragegame.game.handlers;

import static java.lang.Math.min;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ragegame.game.objects.DynamicGameObject.PlayerModel;

public class InputHandler implements InputProcessor {
    PlayerModel playerModel;
    public InputHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            playerModel.move(6);
        } else if (keycode == Input.Keys.A) {
            playerModel.move(4);
        } else if (keycode == Input.Keys.SPACE) {
            playerModel.jumpStart();
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D || keycode == Input.Keys.A) {
            playerModel.move(5);
        } else if (keycode == Input.Keys.SPACE) {
            playerModel.jumpEnd();
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
