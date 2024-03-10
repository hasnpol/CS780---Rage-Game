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
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private Map gameMap;

	private ObjectMap<UUID, Entity> entitiesToDestroy;
	private ObjectMap<UUID, Entity> gameObjects;
	private int screenHeight, screenWidth;

	private PlayerModel playerModel;
	private View playerView;
	private EnemyModel enemyModel;
	private View enemyView;
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
		entitiesToDestroy = new ObjectMap<>();
		gameObjects = new ObjectMap<>();
		this.gameMap = new Map(world, gameObjects);
		this.orthogonalTiledMapRenderer = gameMap.buildMap();
		createPlayer();
		createEnemy();

		// Handle InputProcessor and Contact Listener and Physics Handler
		InputHandler inputHandler = new InputHandler(playerModel);
		Gdx.input.setInputProcessor(inputHandler);
		ContactHandler contactHandler = new ContactHandler(world, gameObjects);
		world.setContactListener(contactHandler);
		physicsHandler = new PhysicsHandler(world, gameObjects);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();

		// Clear previous images drawn to the screen
		ScreenUtils.clear(0, 0, 0, 1);

		// Handle camera logic so that camera follows player within gameMap bounds
		cameraHandler.snapToPlayer(playerModel.getBody().getPosition(), gameMap.getWidth(), gameMap.getHeight());

		// Draw the background
		batch.begin();
		backgroundHandler.render(dt, batch);
		batch.end(); // doing this so that the background is drawn before gameMap don't change this

		// Setup for tiled gameMap to be drawn
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);

		// Draw the tiled gameMap
		batch.begin();
		orthogonalTiledMapRenderer.render();
		playerView.render(dt);

		enemyView.render(dt);
		batch.end();


		batch.begin();
		batch.end();

		// Physics
		world.clearForces();
		playerModel.update();
		physicsHandler.applyForces();
		physicsHandler.doPhysicsStep(dt);
		debugRenderer.render(world, camera.combined);

	}


	private void createPlayer() {
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyDef.BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(0, 10f));

		Body playerBody = world.createBody(playerBodyDef);
		PolygonShape playerBox = new PolygonShape();
		playerBox.setAsBox(0.25f, 0.5f);

		playerModel = new PlayerModel(playerBody);
		playerView = new View(playerModel, batch);
		gameObjects.put(playerModel.getId(), playerModel);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = playerBox;
		fixtureDef.density = 2f;  // more density -> bigger mass for the same size
		fixtureDef.friction = 0;
		playerBody.setFixedRotation(true);
		playerBody.createFixture(fixtureDef).setUserData(playerModel.getId());
		playerBox.dispose();
	}

	private void createEnemy() {
		BodyDef enemyBodyDef = new BodyDef();
		enemyBodyDef.type = BodyDef.BodyType.DynamicBody;
		enemyBodyDef.position.set(new Vector2(10, 10f));

		Body enemyBody = world.createBody(enemyBodyDef);
		PolygonShape enemyBox = new PolygonShape();
		enemyBox.setAsBox(0.25f, 0.5f);

		enemyModel = new EnemyModel(enemyBody);
		enemyView = new View(enemyModel, batch);

		gameObjects.put(enemyModel.getId(), enemyModel);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = enemyBox;
		fixtureDef.density = 2f;  // more density -> bigger mass for the same size
		fixtureDef.friction = 1;

		enemyBody.createFixture(fixtureDef).setUserData(enemyModel.getId());
		enemyBox.dispose();
	}

	@Override
	public void dispose () {
		batch.dispose();
		backgroundHandler.dispose();
		playerView.dispose();
		enemyView.dispose();
		orthogonalTiledMapRenderer.dispose();
	}

}