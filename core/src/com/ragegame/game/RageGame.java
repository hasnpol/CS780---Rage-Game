package com.ragegame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ragegame.game.handlers.ContactHandler;
import com.ragegame.game.handlers.InputHandler;
import com.ragegame.game.handlers.PhysicsHandler;
import com.ragegame.game.objects.GameObject;
import com.ragegame.game.objects.actors.Actors;
import com.ragegame.game.objects.actors.PlayerModel;
import com.ragegame.game.screens.Map;

import java.util.UUID;

public class RageGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private Map map;
	ObjectMap<UUID, Actors> gameObjectsToDestroy;
	ObjectMap<UUID, Actors> gameObjects;
	int height, width;
	PlayerModel playerModel;
	Texture background;
	PhysicsHandler physicsHandler;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("background.png"));

		width = Gdx.graphics.getWidth();
		height =  Gdx.graphics.getHeight();
		camera = new OrthographicCamera(14, 14 * ((float) height / width));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		world = new World(new Vector2(0, -9.8f), true);
		world.setAutoClearForces(false);
		world.setGravity(new Vector2(0, -9.8f));
		debugRenderer = new Box2DDebugRenderer();

		this.map = new Map(world);
		this.orthogonalTiledMapRenderer = map.buildMap();

		gameObjectsToDestroy = new ObjectMap<>();
		gameObjects = new ObjectMap<>();
		createPlayer();

		// Handle InputProcessor and Contact Listener and Physics Handler
		InputHandler inputHandler = new InputHandler(playerModel);
		Gdx.input.setInputProcessor(inputHandler);
		ContactHandler contactHandler = new ContactHandler();
		world.setContactListener(contactHandler);
		physicsHandler = new PhysicsHandler(world, gameObjects);

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		camera.position.set(playerModel.getBody().getPosition(), 0);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);
		batch.begin();
		orthogonalTiledMapRenderer.render();
		batch.end();
		world.clearForces();
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
		gameObjects.put(playerModel.getId(), playerModel);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = playerBox;
		fixtureDef.density = 2f;  // more density -> bigger mass for the same size
		fixtureDef.friction = 1;

		playerBody.createFixture(fixtureDef).setUserData(playerModel.getId());
		playerBox.dispose();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		orthogonalTiledMapRenderer.dispose();
	}

}
