package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * The main screen class used in the game, it is used for all the levels and reads JSON files to create them
 */
public class LevelScreen extends ScreenAdapter{
    private TileMap tileMap;
    //makes an arraylist for all objects in the scene
    ArrayList<GameEntity> scene = new ArrayList<>();
    private SpriteBatch batch;
    private final OrthographicCamera camera;

    private String levelJSON;
    private Player player;

    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();
    private ArrayList<Pot> pots = new ArrayList<>();

    private boolean paused = false;

    //this needs to be public static so it can be accessed by main
    public static int roomTarget = -1;
    //if the player goes off-screen to one of those directions they will go to that room
    private int roomTargetUp;
    private int roomTargetRight;
    private int roomTargetDown;
    private int roomTargetLeft;
    //origins are here for when the player falls into a trap and gets reset to the start of the room
    private float originX;
    private float originY;


    /**
     *
     * @param levelJSON this is a path to the JSON file in assets used to create the level
     * @param batch the spriteBatch used in main is passed through here
     * @param player the player is passed through here
     * @param camera the camera is passed through here
     * @param viewport the viewport is passed through here
     */
	public LevelScreen(String levelJSON, SpriteBatch batch, Player player,
                       OrthographicCamera camera) {
        //gets the spritebatch so we only use 1 global batch which will be disposed upon
        //closing the game
        this.batch = batch;
        this.player = player;
        this.camera = camera;
        this.levelJSON = levelJSON;
        this.paused = false;



        player.position.set(320 - player.getWidthPixels(), 200);
        parseJSON(levelJSON);

        parseJSON(levelJSON);
	}

    /**
     * Parses the JSON file for the level and creates a tileset, spritesheet and list of gameEntities
     * @param levelJSON the level JSON is passed here to be parsed
     */
    private void parseJSON(String levelJSON){
        /*Parses the information from the JSON file given to it in this order:
        {"tileMapInfo" :
        {
          "tileMapPath": [enter a csv file],
          "spriteSheetPath": [enter the path to your spriteSheet],
          "endFrame": [enter an integer for the last sprite in the spriteSheet],
          "roomTargetUp": [enter the screen number this direction will go to, or -1 for none],
          "roomTargetRight": [enter the screen number this direction will go to, or -1 for none],
          "roomTargetDown": [enter the screen number this direction will go to, or -1 for none],
          "roomTargetLeft": [enter the screen number this direction will go to, or -1 for none],
          "originX": [enter pixel location],
          "originY": [enter pixel location]
        },
      "levelObjects": [
          [enter an object type]:
          {
            [enter object initialization attributes]: [enter value]
            ...
          }
        ]}  */

        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(Gdx.files.internal(levelJSON));
        JsonValue tileMapInfo = base.get("tileMapInfo");
        JsonValue levelObjects = base.get("levelObjects");

        //creates the tilemap
        tileMap = new TileMap(tileMapInfo.getString("tileMapPath"), tileMapInfo.getString("spriteSheetPath")
            , tileMapInfo.getInt("endFrame"));

        //assigns the room pointers
        roomTargetUp = tileMapInfo.getInt("roomTargetUp");
        roomTargetRight = tileMapInfo.getInt("roomTargetRight");
        roomTargetDown = tileMapInfo.getInt("roomTargetDown");
        roomTargetLeft = tileMapInfo.getInt("roomTargetLeft");

        //origin of the player
        originX = tileMapInfo.getFloat("originX");
        originY = tileMapInfo.getFloat("originY");

        //adds all the objects
        for (JsonValue value: levelObjects){
            switch (value.getString("type")) {
                case "coin":
                    coins.add(new Coin(value.getFloat("x"),value.getFloat("y")));
                    break;
                case "pot":
                    pots.add(new Pot(value.getFloat("x"),value.getFloat("y")));
                    break;
                case "trap":
                    traps.add(new Trap(value.getFloat("x"),value.getFloat("y")));
                    break;
            }
        }
        scene.addAll(coins);
        scene.addAll(traps);
        scene.addAll(pots);
    }

    /**
     * Checks for inputs to deal with movement physics
     * @param delta deltaTime is passed through here
     * */
    private void update(float delta) {

        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        Vector2 velocity = player.getVelocity(up, down, left, right);

        Physics.moveWithTileCollisions(player, tileMap, delta, velocity.x, velocity.y);

    }



    @Override
    public void show() {
        scene.add(player);
        draw();
    }

    @Override
    public void dispose(){
        tileMap.dispose();
        for (GameEntity obj: scene){
            if (!(obj instanceof Player))
                obj.dispose();
        }
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

    public void spawnCoin(float x, float y){
     	Coin newcoin = new Coin(x,y);
	scene.add(newcoin);
	coins.add(newcoin);
    }

    public void physics(float delta){
        update(delta);

        for (Coin  coin: coins) {
            if (Physics.coinCollision(player, coin)) {
                scene.remove(coin);
                if (!coin.collected) {
                    coin.collected = true;
                    Main.coinCount++;
                }
            }
        }


        for (Trap trap: traps) {
            if (Physics.onTrap(player, trap)) {
                System.out.println(trap.cooldown);
                trap.cooldown -= delta;
                if (Physics.trapTriggered(trap.cooldown)){
                    player.position.set(300, 220);
                    trap.cooldown = 1.5f;
                    Main.trapsTriggered++;
                }
            } else { trap.cooldown = 1.5f; }
        }


        for (Pot pot : pots) {
            if (Physics.onPot(pot, player, tileMap)) {
                Main.potsBroken++;
                if (Math.random() < 0.5f) {
                    spawnCoin(pot.getPos().x, pot.getPos().y);
                }
            }
        }

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
