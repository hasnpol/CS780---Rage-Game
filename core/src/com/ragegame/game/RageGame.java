package com.ragegame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;

import com.ragegame.game.handlers.BackgroundHandler;
import com.ragegame.game.handlers.CameraHandler;
import com.ragegame.game.handlers.ContactHandler;
import com.ragegame.game.handlers.InputHandler;
import com.ragegame.game.handlers.PhysicsHandler;
import com.ragegame.game.objects.DynamicEntity.PlayerModel;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.objects.DynamicEntity.EnemyModel;
import com.ragegame.game.objects.view.View;

import com.ragegame.game.screens.Map;

import java.util.UUID;

public class RageGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
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

	@Override
	public void create() {
		batch = new SpriteBatch();

		// Init Camera
		screenWidth = Gdx.graphics.getWidth();
		screenHeight =  Gdx.graphics.getHeight();
		camera = new OrthographicCamera(15 , 15 * ((float) screenHeight / screenWidth));
		cameraHandler = new CameraHandler(camera);

		// Init backgrounds
		backgroundHandler = new BackgroundHandler();

		// Init the world
		world = new World(new Vector2(0, -9.8f), true);
		world.setAutoClearForces(false);
		world.setGravity(new Vector2(0, -9.8f));
		debugRenderer = new Box2DDebugRenderer();

		// Init game objects
		gameObjectsToDestroy = new ObjectMap<>();
		gameObjects = new ObjectMap<>();
		this.gameMap = new Map(world, gameObjects, batch, camera);

		// Handle InputProcessor and Contact Listener and Physics Handler
		InputHandler inputHandler = new InputHandler(gameMap.playerModel);
		Gdx.input.setInputProcessor(inputHandler);
		ContactHandler contactHandler = new ContactHandler(world, gameObjects);
		world.setContactListener(contactHandler);
		physicsHandler = new PhysicsHandler(world, gameObjects);

	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();

		// Clear previous images drawn to the screen
		ScreenUtils.clear(0, 0, 0, 1);

		// Handle camera logic so that camera follows player within gameMap bounds
		cameraHandler.snapToPlayer(gameMap.playerModel.getBody().getPosition(), gameMap.getWidth(), gameMap.getHeight());

		// Draw the background
		batch.begin();
		backgroundHandler.render(dt, batch, gameMap.getWidth(), gameMap.getHeight(), gameMap.getPPM());
		batch.end(); // doing this so that the background is drawn before gameMap don't change this

		// Setup for tiled gameMap to be drawn
		batch.setProjectionMatrix(camera.combined);

		// Draw the tiled gameMap
		batch.begin();
		gameMap.render(dt);
		batch.end();

		// Physics
		world.clearForces();
		gameMap.playerModel.update();
		physicsHandler.applyForces();
		physicsHandler.doPhysicsStep(dt);
		//debugRenderer.render(world, camera.combined);

	}

	@Override
	public void dispose () {
		batch.dispose();
		backgroundHandler.dispose();
		gameMap.dispose();
	}

}