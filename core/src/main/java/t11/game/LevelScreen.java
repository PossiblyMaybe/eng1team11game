package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class LevelScreen extends ScreenAdapter{

    //public boolean paused = false;
    private TileMap tileMap;
    //makes an arraylist for all objects in the scene
    ArrayList<GameEntity> scene = new ArrayList<GameEntity>();
    private SpriteBatch batch;
    private final OrthographicCamera camera;
    private String levelJSON;
    private Player player;

    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();

    private boolean paused = false;

    //this needs to be public static so it can be accessed by main
    public static int roomTarget = -1;
    private int roomTargetUp;
    private int roomTargetRight;
    private int roomTargetDown;
    private int roomTargetLeft;
    private float originX;
    private float originY;



	public LevelScreen(String levelJSON, SpriteBatch batch, Player player,
                       OrthographicCamera camera, Viewport viewport) {
        //gets the spritebatch so we only use 1 global batch which will be disposed upon
        //closing the game
        this.batch = batch;
        this.player = player;
        this.camera = camera;
        this.levelJSON = levelJSON;
        this.paused = false;


        player.position.set(320 - player.getWidthPixels(), 200);

	}

    private void parseJSON(String levelJSON){
        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(Gdx.files.internal(levelJSON));
        JsonValue tileMapInfo = base.get("tileMapInfo");
        JsonValue levelObjects = base.get("levelObjects");

        tileMap = new TileMap(tileMapInfo.getString("tileMapPath"), tileMapInfo.getString("spriteSheetPath")
            , tileMapInfo.getInt("endFrame"));

        roomTargetUp = tileMapInfo.getInt("roomTargetUp");
        roomTargetRight = tileMapInfo.getInt("roomTargetRight");
        roomTargetDown = tileMapInfo.getInt("roomTargetDown");
        roomTargetLeft = tileMapInfo.getInt("roomTargetLeft");

        originX = tileMapInfo.getFloat("originX");
        originY = tileMapInfo.getFloat("originY");

        for (JsonValue value: levelObjects){
            switch (value.getString("type")) {
                case "coin":
                    coins.add(new Coin(value.getFloat("x"),value.getFloat("y")));
                    break;
                case "pot":
                    break;
                case "trap":
                    traps.add(new Trap(value.getFloat("x"),value.getFloat("y")));
                    break;
            }
        }
        scene.addAll(coins);
        scene.addAll(traps);
    }


    private void update(float delta) {
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        Vector2 vel = player.getVelocity(up, down, left, right);



        Physics.moveWithTileCollisions(player, tileMap, delta, vel.x, vel.y);

    }



    @Override
    public void show() {
        parseJSON(levelJSON);
        scene.add(player);
        draw();

    }

    @Override
    public void dispose(){
        //does nothing :)
    }

    @Override
    public void render(float delta){

        if (!paused)
            physics(delta);
        draw();
    }

    public void draw(){
        batch.begin();

        for (Tile[] tileRow: tileMap.getTiles()){
            for (Tile tile: tileRow){
                if (tile.getSprite() != null){
                    batch.draw(tile.getSprite(), tile.getPos().x, tile.getPos().y, 40, 40);
                }
            }
        }

        for (GameEntity obj: scene){
            batch.draw(obj.getSprite(), obj.getPos().x, obj.getPos().y, 16*obj.getScale().x, 16*obj.getScale().y);
        }
            batch.end();

    }

    public void physics(float delta){
        update(delta);

        if (player.position.x < 0){
            player.position.x = 599;
            roomTarget = roomTargetLeft;

        } else if (player.position.x > 600){
            player.position.x = 1;
            roomTarget = roomTargetRight;
        }

        if (player.position.y < 0){
            player.position.y = 439;
            roomTarget = roomTargetDown;
        } else if (player.position.y > 440) {
            player.position.y = 1;
            roomTarget = roomTargetUp;
        }

    }

    @Override
    public void pause(){
        paused = true;
        System.out.println("Paused");
    }

    @Override
    public void resume(){
        paused = false;
        System.out.println("Unpaused");
    }


}

