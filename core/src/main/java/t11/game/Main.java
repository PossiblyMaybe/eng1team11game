package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    //I reimplemented SpriteBatch since SpriteCache is a pain, and we aren't drawing enough sprites
    //on the screen to warrant needing SpriteCache. If we had a large open world it would make sense,
    //but not if we are only drawing one screen at a time
    public SpriteBatch batch;

    private OrthographicCamera camera;
    private FitViewport viewport;

    // Window size constants
    private static final float WINDOW_WIDTH = 800;
    private static final float WINDOW_HEIGHT = 600;

    //this is some horrendous implementation, sorry
    private float dashCooldown = 0;
    private final Vector2 direction = new Vector2(0,0);


	private ScreenDispatch screens;

    private TileMap tilemap;

    //Variables for the timer coins and score, they are currently unused
    public int coinCount = 0;
    public int score = 0;
    public float timeRemaining = 300;

    public Player player;

	@Override
	public void create() {
        batch = new SpriteBatch();

        // Creates the orthographic camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT, camera);

        player = new Player();

        //Adds the test screen to the list of screens, this can later be replaced with
        //an algorithm to select a bunch of screens
        screens = new ScreenDispatch(new TestScreen(batch));

        setScreen(screens.getScreen());

        tilemap = new TileMap("testMap.csv");

	}

    @Override
    public void render() {
        // Updates the camera
        camera.update();

        //idk what this does tbh i just know i need it
        batch.setProjectionMatrix(camera.combined);

        input();
        physics();
        //clears the screen before drawing
        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tilemap.render(batch);
        super.render(); //renders the current screen
        draw();
	}

    @Override
    public void resize(int width, int height) {
        // Update the viewport and re-center the camera
        viewport.update(width, height);
        camera.position.set(WINDOW_WIDTH / 2f, WINDOW_HEIGHT / 2f, 0);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void input()
    {
        //This grabs the input for the player, currently it checks for
        //the arrow keys and the space bar
        float delta = Gdx.graphics.getDeltaTime();
        boolean isDashing;
        if (dashCooldown > 2.75)
            isDashing = true;
        else {
            isDashing = false;
            direction.x = 0;
            direction.y = 0;
        }

        //something to note is that I made it so that you can change the direction while dashing
        if (!isDashing) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                direction.y += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                direction.y -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                direction.x -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                direction.x += 1;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && dashCooldown <= 0) {
            isDashing = true;
            dashCooldown = 3;
        }
        player.move(direction, isDashing, delta);
        dashCooldown -= delta;
    }

    public void physics(){
        //currently does nothing :)
    }

    public void draw(){
        //.draw() can do many things, the origins lets you draw from a different place that isn't 0,0 relative
        //to the texture, width and height let you crow textures, rotation is self explanatory
        //and the rest are for drawing certain sections of a texture which can be used to make
        //spritesheets work
        //batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, 0, 0, texWidth, texHeight, false, false);
        batch.begin();
        //draws the player on top, this should later be changed to implement a Z indexing
        batch.draw(player.getSprite(), player.getPos().x, player.getPos().y, 0, 0, 32, 32, player.getScale().x, player.getScale().y, player.getRotation(), 0, 0, 32, 32, false, false);
        batch.end();
    }

}
