package com.ragegame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ragegame.game.handlers.ContactHandler;
import com.ragegame.game.handlers.InputHandler;
import com.ragegame.game.handlers.PhysicsHandler;
import com.ragegame.game.objects.actors.Actors;
import com.ragegame.game.objects.actors.PlayerModel;
import com.ragegame.game.screens.Map;

import java.util.ArrayList;
import java.util.UUID;

public class RageGame extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	public static World world;
	Box2DDebugRenderer debugRenderer;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private Map map;
	ObjectMap<UUID, Actors> gameObjectsToDestroy;
	ObjectMap<UUID, Actors> gameObjects;
	int height, width;
	PlayerModel playerModel;
	Texture background;
	PhysicsHandler physicsHandler;

	ArrayList<Texture> backgrounds = new ArrayList<>();
	ArrayList<Float> backgroundOffsets = new ArrayList<>();
	float backgroundSpeed = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();

//		backgrounds.add(new Texture(Gdx.files.internal("maps/desert/BG.png")));
		backgrounds.add(new Texture(Gdx.files.internal("backgrounds/donghun-lee-2-01-back.jpg")));
		backgrounds.add(new Texture(Gdx.files.internal("backgrounds/donghun-lee-2-02-middle.jpg")));
		backgrounds.add(new Texture(Gdx.files.internal("backgrounds/donghun-lee-2-03-front.jpg")));
		backgrounds.add(new Texture(Gdx.files.internal("backgrounds/donghun-lee-2-04-floor.jpg")));

		width = Gdx.graphics.getWidth();
		height =  Gdx.graphics.getHeight();
		camera = new OrthographicCamera(15 , 15 * ((float) height / width));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		world = new World(new Vector2(0, -9.8f), true);
		world.setAutoClearForces(false);
		world.setGravity(new Vector2(0, -9.8f));
		debugRenderer = new Box2DDebugRenderer();

		gameObjectsToDestroy = new ObjectMap<>();
		gameObjects = new ObjectMap<>();
		this.map = new Map(world, gameObjects);
		this.orthogonalTiledMapRenderer = map.buildMap();
		createPlayer();

		// Handle InputProcessor and Contact Listener and Physics Handler
		InputHandler inputHandler = new InputHandler(playerModel);
		Gdx.input.setInputProcessor(inputHandler);
		ContactHandler contactHandler = new ContactHandler(world, gameObjects);
		world.setContactListener(contactHandler);
		physicsHandler = new PhysicsHandler(world, gameObjects);

	}

	@Override
	public void render () {

		float mapHeight = 18;
		float mapWidth = 120;

		float dt = Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(0, 0, 0, 1);
		Vector2 playerPos = playerModel.getBody().getPosition();
		camera.position.set(playerPos, 0);
		camera.update();
		cameraBoundary(camera, camera.viewportWidth/2f, camera.viewportHeight/2f, mapWidth-camera.viewportWidth/2f*2, mapHeight-camera.viewportHeight/2f*2);
		camera.update();


		batch.begin();
		renderBackground(dt);
		batch.end();

		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);

		batch.begin();
		orthogonalTiledMapRenderer.render();
		batch.end();

		world.clearForces();
		playerModel.update();
		physicsHandler.applyForces();
		physicsHandler.doPhysicsStep(dt);
		debugRenderer.render(world, camera.combined);

	}

	private void renderBackground(float deltaTime) {
		float worldHeight = 18 * 32;
		float worldWidth = 128 * 32;

		backgroundOffsets.clear();

		backgroundOffsets.add(deltaTime * 1);
		backgroundOffsets.add(deltaTime * 2);
		backgroundOffsets.add(deltaTime * 3);
		backgroundOffsets.add(deltaTime * 4);

		for (int i = 0; i < backgroundOffsets.size(); i++) {
			if (backgroundOffsets.get(i) > worldHeight) {
				backgroundOffsets.set(i, 0f);
			}
			batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i), worldWidth/32f, worldHeight/32f);
			batch.draw(backgrounds.get(i), 0, -backgroundOffsets.get(i) + (worldWidth/32f), worldWidth/32f, worldHeight/32f);
		}

	}

	public void cameraBoundary(OrthographicCamera camera, float startX, float startY, float width, float height) {
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
		fixtureDef.friction = 0;
		playerBody.setFixedRotation(true);
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