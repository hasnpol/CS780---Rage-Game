package com.ragegame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ragegame.game.RageGame;
import com.ragegame.game.handlers.BackgroundHandler;
import com.ragegame.game.handlers.CameraHandler;
import com.ragegame.game.handlers.ContactHandler;
import com.ragegame.game.handlers.InputHandler;
import com.ragegame.game.handlers.PhysicsHandler;
import com.ragegame.game.objects.Entity;

import java.util.UUID;

public class GameScreen implements Screen {

    private RageGame game;
    public OrthographicCamera camera;

    public static World world;
    private Box2DDebugRenderer debugRenderer;
    private Map gameMap;

    private ObjectMap<UUID, Entity> gameObjectsToDestroy;
    private ObjectMap<UUID, Entity> gameObjects;
    private int screenHeight, screenWidth;

    //private PlayerModel playerModel;
    //private View playerView;
    ////private EnemyModel enemyModel;
    //private View enemyView;
    private PhysicsHandler physicsHandler;
    private CameraHandler cameraHandler;
    private BackgroundHandler backgroundHandler;


    public GameScreen(RageGame game) {
        this.game = game;

        // Init Camera
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight =  Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(15 , 15 * ((float) screenHeight / screenWidth));
        this.cameraHandler = new CameraHandler(camera);

        // Init backgrounds
        this.backgroundHandler = new BackgroundHandler();

        // Init the world
        world = new World(new Vector2(0, -9.8f), true);
        world.setAutoClearForces(false);
        world.setGravity(new Vector2(0, -9.8f));
        this.debugRenderer = new Box2DDebugRenderer();

        // Init game objects
        this.gameObjectsToDestroy = new ObjectMap<>();
        this.gameObjects = new ObjectMap<>();
        this.gameMap = new Map(world, gameObjects, game.batch, camera);

        // Handle InputProcessor and Contact Listener and Physics Handler
        InputHandler inputHandler = new InputHandler(gameMap.playerModel);
        Gdx.input.setInputProcessor(inputHandler);
        ContactHandler contactHandler = new ContactHandler(world, gameObjects);
        world.setContactListener(contactHandler);
        this.physicsHandler = new PhysicsHandler(world, gameObjects);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        float dt = Gdx.graphics.getDeltaTime();

		// Clear previous images drawn to the screen
		ScreenUtils.clear(0, 0, 0, 1);

		// Handle camera logic so that camera follows player within gameMap bounds
		this.cameraHandler.snapToPlayer(gameMap.playerModel.getBody().getPosition(), gameMap.getWidth(), gameMap.getHeight());

		// Draw the background
		game.batch.begin();
		this.backgroundHandler.render(dt, game.batch, gameMap.getWidth(), gameMap.getHeight(), gameMap.getPPM());
		game.batch.end(); // doing this so that the background is drawn before gameMap don't change this

		// Setup for tiled gameMap to be drawn
		game.batch.setProjectionMatrix(camera.combined);

		// Draw the tiled gameMap
		game.batch.begin();
		this.gameMap.render(dt);
		game.batch.end();

		// Physics
		world.clearForces();
		this.gameMap.playerModel.update();
		this.physicsHandler.applyForces();
		this.physicsHandler.doPhysicsStep(dt);
		//debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        this.backgroundHandler.dispose();
        this.gameMap.dispose();
    }
}
