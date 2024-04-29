package com.ragegame.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.utils.Constants.*;
import com.ragegame.game.utils.Constants.Direction.*;

public class InputHandler implements InputProcessor {
    PlayerModel playerModel;
    public InputHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (!playerModel.isDead()) {
            if (keycode == Input.Keys.D) {
                playerModel.move(Direction.RIGHT);
                playerModel.setMovementVector(new Vector2(1, 0));
            } else if (keycode == Input.Keys.A) {
                playerModel.move(Direction.LEFT);
                playerModel.setMovementVector(new Vector2(-1, 0));
            } else if (keycode == Input.Keys.SPACE) {
                playerModel.jumpStart();
            } else if (keycode == Input.Keys.SHIFT_LEFT) {
                playerModel.sprint();
            }
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if (!playerModel.isDead()) {
            if (keycode == Input.Keys.D || keycode == Input.Keys.A) {
                playerModel.move(Direction.STOP);
                playerModel.setMovementVector(new Vector2(0, 0));
            } else if (keycode == Input.Keys.SPACE) {
                playerModel.jumpEnd();
                playerModel.setMovementVector(new Vector2(playerModel.getMovementVector().x, 1));
            } else if (keycode == Input.Keys.SHIFT_LEFT) {
                playerModel.sprint();
            }
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
