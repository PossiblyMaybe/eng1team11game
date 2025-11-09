package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.logging.Level;

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

	public ScreenDispatch screens;

    //Variables for the timer coins and score, they are currently unused
    public static int coinCount;
    public static int potsBroken;
    public static int trapsTriggered;
    public static float timeRemaining = 300;

    public Player player;
    private Texture clock;
    private Texture guiPiece;
    private Texture pausedGUI;

    //paused bool
    private boolean paused = false;
    private boolean gameOver = false;

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
        pausedGUI = new Texture("paused.png");


        screens = new ScreenDispatch(new LevelScreen("levelJ0.json", batch, player, camera, viewport));
        screens.addScreen(new LevelScreen("levelJ1.json", batch, player, camera, viewport));
        screens.addScreen(new LevelScreen("levelJ2.json", batch, player, camera, viewport));
        screens.addScreen(new endScreen(batch, font));

        setScreen(screens.getScreen());

        //

	}

    @Override
    public void render() {
        // Updates the camera
        camera.update();

        input();

        //idk what this does tbh i just know i need it
        batch.setProjectionMatrix(camera.combined);

        //clears the screen before drawing
        if (gameOver){
            Gdx.gl.glClearColor(0.0f,255.0f,0.0f,1.0f);
        } else {
            Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(); //renders the current screen
        guiDraw();
        logic();
        roomChangeCheck();
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
        font.dispose();
    }

    public void input() {


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

        if (!gameOver)
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
        String minSecText;
        if (timeRemaining % 60 < 10){
            minSecText = minutes.concat(":0" + seconds);
        } else {
            minSecText = minutes.concat(":" + seconds);
        }
        font.draw(batch, "Time remaining: ".concat(minSecText), 30, 470);
        batch.draw(clock, 7, 455);
        //event counters
        font.draw(batch, "Coins collected: ".concat(Integer.toString(coinCount)), 500, 470);
        font.draw(batch, "Pots broken: ".concat(Integer.toString(potsBroken)), 500, 450);
        font.draw(batch, "Traps triggered: ".concat(Integer.toString(trapsTriggered)), 500, 430);

        if (paused)
            batch.draw(pausedGUI, 176,312, 288, 96);

        batch.end();
    }

    public void logic(){
        if (!paused && !gameOver)
            timeRemaining -= Gdx.graphics.getDeltaTime();

        if (timeRemaining < 0){
            screens.goToLast();
            setScreen(screens.getScreen());
            gameOver = true;
        }
    }

    public void roomChangeCheck(){
        if (LevelScreen.roomTarget != -1){
            screens.gotoScreen(LevelScreen.roomTarget);
            setScreen(screens.getScreen());
            LevelScreen.roomTarget = -1;
            if (screens.isLast())
                gameOver = true;
        }
    }



}
