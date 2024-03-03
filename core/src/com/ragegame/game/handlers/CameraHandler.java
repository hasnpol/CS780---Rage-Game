package com.ragegame.game.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraHandler {
    private OrthographicCamera camera;
    public CameraHandler(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void set(Vector3 position) {
        camera.position.set(position);
    }

    public void snapToPlayer(Vector2 playerPos, float mapWidth, float mapHeight) {
        camera.position.set(playerPos, 0);
        float startX = camera.viewportWidth/2f;
        float startY = camera.viewportHeight/2f;
        enforceBoundaries(this.camera, startX, startY, mapWidth-startX*2, mapHeight-startY*2);
        camera.update();
    }

    public void enforceBoundaries(OrthographicCamera camera, float startX, float startY, float width, float height) {
        Vector3 position = camera.position;

        if (position.x < startX)
            position.x = startX;
        if (position.y < startY)
            position.y = startY;

        if (position.x > startX + width)
            position.x = startX + width;
        if (position.y > startY + height)
            position.y = startY + height;

        camera.position.set(position);
        camera.update();
    }


}
