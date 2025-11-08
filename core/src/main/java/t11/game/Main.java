package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    //I reimplemented SpriteBatch since SpriteCache is a pain, and we aren't drawing enough sprites
    //on the screen to warrant needing SpriteCache. If we had a large open world it would make sense,
    //but not if we are only drawing one screen at a time
    public SpriteBatch batch;
    public BitmapFont font;

    private OrthographicCamera camera;
    private FitViewport viewport;

    // Window size constants
    private static final float WINDOW_WIDTH = 640;
    private static final float WINDOW_HEIGHT = 480;

	private ScreenDispatch screens;

    //Variables for the timer coins and score, they are currently unused
    public int coinCount = 0;
    public int score = 0;
    public float timeRemaining = 300;

    public Player player;
    private Texture clock;
    private Texture guiPiece;

    //paused bool
    private boolean paused;

	@Override
	public void create() {
        //drawing stuff definitions
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Creates the orthographic camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT, camera);
        camera.position.set(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 0);
        camera.update();

        Gdx.graphics.setWindowedMode(800,600);

        player = new Player();
        clock = new Texture("Clock.png");
        guiPiece = new Texture("gui piece.png");
        paused = false;

        screens = new ScreenDispatch(new LevelScreen("testJ.json", batch, player, camera, viewport));

        setScreen(screens.getScreen());
	}

    @Override
    public void render() {
        // Updates the camera
        camera.update();

        //idk what this does tbh i just know i need it
        batch.setProjectionMatrix(camera.combined);

        input();
        //clears the screen before drawing
        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(); //renders the current screen
        guiDraw();
        if (!paused)
            timeRemaining -= Gdx.graphics.getDeltaTime();
	}

    @Override
    public void resize(int width, int height) {
        // Update the viewport and re-center the camera
        viewport.update(width, height, true);
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
        Vector2 direction = new Vector2(0,0);

        //something to note is that I made it so that you can change the direction while dashing
        if (Gdx.input.isKeyPressed(Input.Keys.UP) ^ Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction.y = Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ^ Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction.x = Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : -1;
        }

        player.move(direction, Gdx.input.isKeyPressed(Input.Keys.SPACE), Gdx.graphics.getDeltaTime(), paused);


        //test for pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){

            if (paused){
                paused = false;
                screens.getScreen().resume();
            } else {
                paused = true;
                screens.getScreen().pause();
            }
        }

    }

    public void guiDraw(){
        batch.begin();
        //draw gui elements here
        batch.draw(guiPiece, 0, 448, 0, 0, 32, 16, 6, 2, 0, 0, 0, 32, 16, false, false);
        String minutes = Integer.toString((int) timeRemaining / 60);
        String seconds = Integer.toString((int) timeRemaining % 60);
        String minSecText = minutes.concat(":" + seconds);
        font.draw(batch, "Time remaining: ".concat(minSecText), 30, 470);
        batch.draw(clock, 7, 455);
        batch.end();
    }



}
