package com.ragegame.game;

import static java.lang.Math.min;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import java.util.UUID;

//<<<<<<< HEAD
//public class RageGame extends Game {
//
//	public static RageGame INSTANCE;
//	private int width, height;
//	private OrthographicCamera camera;
//	private Screen screen;
//
//
//	@Override
//	public void create() {
//		this.width = Gdx.graphics.getWidth();
//		this.height = Gdx.graphics.getHeight();
//		this.camera = new OrthographicCamera();
//		this.camera.setToOrtho(false, width, height);
//		this.screen = new Screen(camera);
//=======

public class RageGame extends ApplicationAdapter implements InputProcessor, ContactListener {
	public static final float TIME_STEP = 1/60F;

	OrthographicCamera camera;
	Screen screen;
	SpriteBatch batch;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;
	private float accumulator = 0;

	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private Map map;
	ObjectMap<UUID, GameObject> gameObjectsToDestroy;
	ObjectMap<UUID, GameObject> gameObjects;

	long chargeStartTime;

	int height, width;

	PlayerModel playerModel;

	private void initBodies() {
		createPlayer();
		//createGround();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		width = Gdx.graphics.getWidth();
		height =  Gdx.graphics.getHeight();
		camera = new OrthographicCamera(14, 14 * ((float) height / width));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		world = new World(new Vector2(0, -9.8f), true);world.setContactListener(this);
		world.setContactListener(this);
		world.setAutoClearForces(false);
		world.setGravity(new Vector2(0, -9.8f));
		debugRenderer = new Box2DDebugRenderer();

		this.map = new Map(world);
		this.orthogonalTiledMapRenderer = map.buildMap();

		gameObjectsToDestroy = new ObjectMap<>();
		gameObjects = new ObjectMap<>();
		initBodies();
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		orthogonalTiledMapRenderer.render();
		world.step(1 / 60f, 6, 2);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);
		batch.begin();
		batch.end();
		world.clearForces();
		applyForces();
		doPhysicsStep(dt);
		debugRenderer.render(world, camera.combined);

	}

	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, 6, 2);
			accumulator -= TIME_STEP;
		}
	}

	private void createGround() {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 2));

		// Create a body from the definition and add it to the world
		Body groundBody = world.createBody(groundBodyDef);
		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box
		groundBox.setAsBox(camera.viewportWidth, 2);
		// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f).setFriction(0.1f);
		// Clean up after ourselves
		groundBox.dispose();

	}

	private void createPlayer() {
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyDef.BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(0, 10f));

		Body playerBody = world.createBody(playerBodyDef);
		PolygonShape playerBox = new PolygonShape();
		playerBox.setAsBox(0.2f, 0.2f);

		playerModel = new PlayerModel(playerBody);
		gameObjects.put(playerModel.getId(), playerModel);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = playerBox;
		fixtureDef.density = 10f;  // more density -> bigger mass for the same size
		fixtureDef.friction = 8;

		playerBody.createFixture(fixtureDef).setUserData(playerModel.getId());
		playerBox.dispose();
	}

	private void applyForces() {
		for (ObjectMap.Entries<UUID, GameObject> entries = this.gameObjects.iterator(); entries.hasNext(); ) {
			ObjectMap.Entry<UUID, GameObject> b = entries.next();
			b.value.applyForces();
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
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

	@Override
	public void beginContact(Contact contact) {

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
