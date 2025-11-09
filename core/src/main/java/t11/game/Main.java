package t11.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public ScreenDispatch screens;

    //Variables for the timer coins and score
    //They need to be public static so they can be accessed from elsewhere
    public static int coinCount = 0;
    public static int potsBroken = 0;
    public static int trapsTriggered = 0;
    public static float timeRemaining = 300;

    public Player player;
    private Texture clock;
    private Texture guiPiece;
    private Texture pausedGUI;

    //paused and gameOver bool
    private boolean paused = false;
    private boolean gameOver = false;

    @Override
	public void create() {
        /*Called when the game starts */
        //drawing stuff definitions
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Window stuff
        camera = new OrthographicCamera();
        viewport = new FitViewport(WINDOW_WIDTH, WINDOW_HEIGHT, camera);
        camera.position.set(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, 0);
        camera.update();

        Gdx.graphics.setWindowedMode(800,600);

        //Instantiating player and some other stuff
        player = new Player();
        clock = new Texture("Clock.png");
        guiPiece = new Texture("gui piece.png");
        pausedGUI = new Texture("paused.png");

        //Adds screens to the screens list. Currently hardcoded
        screens = new ScreenDispatch(new LevelScreen("levelJ0.json", batch, player, camera, viewport));
        screens.addScreen(new LevelScreen("levelJ1.json", batch, player, camera, viewport));
        screens.addScreen(new LevelScreen("levelJ2.json", batch, player, camera, viewport));
        screens.addScreen(new endScreen(batch, font));

        setScreen(screens.getScreen());

	}

    @Override
    public void render() {
        // Updates the camera
        camera.update();

        //checks for inputs
        input();

        batch.setProjectionMatrix(camera.combined);

        //clears the screen before drawing
        if (gameOver){
            Gdx.gl.glClearColor(0.0f,255.0f,0.0f,1.0f);
        } else {
            Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renders the current screen
        super.render();
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
        player.dispose();
        clock.dispose();
        guiPiece.dispose();
        pausedGUI.dispose();
    }

    public void input() {
        /*Checks for the movement keys (Up, down, left and right) and for the space bar and escape key */

        //direction the player is moving
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


        //checks for the escape key to pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !gameOver){

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
        /*Draws gui elements like the timer, pause menu and the event counters */
        batch.begin();
        //draw gui elements here
        batch.draw(guiPiece, 0, 448, 32*6, 16*2);
        String minutes = Integer.toString((int) timeRemaining / 60);
        String seconds = Integer.toString((int) timeRemaining % 60);
        String minSecText;
        if (timeRemaining % 60 < 10){
            minSecText = minutes.concat(":0" + seconds);
        } else {
            minSecText = minutes.concat(":" + seconds);
        }
        //draws timer
        font.draw(batch, "Time remaining: ".concat(minSecText), 30, 470);
        batch.draw(clock, 7, 455);
        //event counters
        font.draw(batch, "Coins collected: ".concat(Integer.toString(coinCount)), 500, 470);
        font.draw(batch, "Pots broken: ".concat(Integer.toString(potsBroken)), 500, 450);
        font.draw(batch, "Traps triggered: ".concat(Integer.toString(trapsTriggered)), 500, 430);

        if (paused) //draws the pause screen if paused
            batch.draw(pausedGUI, 176,312, 288, 96);

        batch.end();
    }

    public void logic(){
        /*Deals with timer related logic, like decrementing time by deltaTime each frame and ending the game if you run out of time */
        if (!paused && !gameOver) //decrements time
            timeRemaining -= Gdx.graphics.getDeltaTime();

        if (timeRemaining < 0){ //ends the game if you run out of time
            screens.goToLast();
            setScreen(screens.getScreen());
            gameOver = true;
            paused = false;
        }
    }

    public void roomChangeCheck(){
        /*Checks to see if LevelScreen wants to change screen because the player went off-screen*/
        if (LevelScreen.roomTarget != -1){
            screens.gotoScreen(LevelScreen.roomTarget);
            setScreen(screens.getScreen());
            LevelScreen.roomTarget = -1;
            if (screens.isLast()) //if the screen is the last one then set gameOver to true since it's the win screen
                gameOver = true;
        }
    }



}
