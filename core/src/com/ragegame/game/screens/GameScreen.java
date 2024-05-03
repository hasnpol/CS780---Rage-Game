package com.ragegame.game.screens;

import static com.ragegame.game.utils.Constants.PlayerConstants.HEALTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ragegame.game.RageGame;
import com.ragegame.game.factory.BombFactory;
import com.ragegame.game.factory.BulletFactory;
import com.ragegame.game.handlers.BackgroundHandler;
import com.ragegame.game.handlers.CameraHandler;
import com.ragegame.game.handlers.ContactHandler;
import com.ragegame.game.handlers.InputHandler;
import com.ragegame.game.handlers.PhysicsHandler;
import com.ragegame.game.map.Map;
import com.ragegame.game.objects.Entity;
import com.ragegame.game.utils.Constants;
import com.ragegame.game.utils.HUD;

import java.util.UUID;

public class GameScreen implements Screen {
    final RageGame game;
    public OrthographicCamera camera;
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    private Map gameMap;
    private ObjectMap<UUID, Entity> gameObjectsToDestroy;
    private ObjectMap<UUID, Entity> gameObjects;
    private int screenHeight, screenWidth;
    private PhysicsHandler physicsHandler;
    private CameraHandler cameraHandler;
    private BackgroundHandler backgroundHandler;

    Texture blank;

    private HUD hud;


    public GameScreen(RageGame game) {
        this.game = game;

        // Init Camera
        this.screenWidth = Constants.Game.WIDTH;
        this.screenHeight =  Constants.Game.HEIGHT;
        this.camera = new OrthographicCamera(20, 20 * ((float) screenHeight / screenWidth));
        this.hud = new HUD(game.batch);
        this.cameraHandler = new CameraHandler(camera);

        this.blank = new Texture("blank.png");

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

        BulletFactory bulletFactory = BulletFactory.getInstance();
        bulletFactory.gameObjectsToDestroy = gameObjectsToDestroy;
        bulletFactory.gameObjects = gameObjects;
        bulletFactory.world = world;

        BombFactory bombFactory = BombFactory.getInstance();
        bombFactory.gameObjectsToDestroy = gameObjectsToDestroy;
        bombFactory.gameObjects = gameObjects;
        bombFactory.world = world;

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
        if (!gameMap.playerModel.isDead()) {
            this.cameraHandler.snapToPlayer(gameMap.playerModel.getBody().getPosition(), gameMap.getWidth(), gameMap.getHeight());
        }

		// Draw the background
        game.batch.begin();
        this.backgroundHandler.render(dt, game.batch, RageGame.V_Width + 200, RageGame.V_Height + 200, gameMap.getPPM());
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.end(); // doing this so that the background is drawn before gameMap don't change this

		// Draw the tiled gameMap
        game.batch.begin();
		this.gameMap.render(dt);
		game.batch.end();

		// Physics
		world.clearForces();
        this.gameMap.updateDynamic();
		this.physicsHandler.applyForces();
		this.physicsHandler.doPhysicsStep(dt);
        deleteMarkedObjects();
		// debugRenderer.render(world, camera.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.addCoins(gameMap.playerModel.getCoins());
        hud.addMedals(gameMap.playerModel.getMedals());


        game.batch.begin();
        //Draw health
        if (gameMap.playerModel.getHealth() > HEALTH * 0.50) {
            game.batch.setColor(Color.GREEN);
        }else if (gameMap.playerModel.getHealth() > HEALTH * 0.25){
            game.batch.setColor(Color.ORANGE);
        }else {
            game.batch.setColor(Color.RED);
        }

        // Health Bar
        game.batch.draw(blank, 20, 380, (RageGame.V_Width *
                ((float) gameMap.playerModel.getHealth() / HEALTH))/5, 10);
        game.batch.setColor(Color.WHITE);
        game.batch.end();

        // Check if we win
        if (gameMap.didWin()) {
            game.account.setCurrency(gameMap.playerModel.getCoins());
            game.account.flush();
            game.changeScreen(new GameOver(game));
            this.dispose();
        }

        // Check if we died
        if (gameMap.playerModel.isDead()) {
            game.account.setCurrency(gameMap.playerModel.getCoins());
            game.account.flush();
            game.changeScreen(new GameOver(game));
            this.dispose();
        }
    }

    public void deleteMarkedObjects() {
        for (ObjectMap.Entry<UUID, Entity> entry : gameObjects) {
            Entity entity = entry.value;
            if (entity.needsDeletion())
                gameObjectsToDestroy.put(entry.key, entity);
        }
        for (ObjectMap.Entry<UUID, Entity> entry : gameObjectsToDestroy) {
            entry.value.delete(world);
            gameObjects.remove(entry.key);
        }
        gameObjectsToDestroy.clear();
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
        this.backgroundHandler.dispose();
        this.gameMap.dispose();
        world.dispose();
        this.debugRenderer.dispose();
        hud.dispose();
    }
}
