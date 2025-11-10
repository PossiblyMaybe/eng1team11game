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


public class LevelScreen extends ScreenAdapter{

    //public boolean paused = false;
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

    public int score = 0;

    private boolean paused;





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

	}

    private void parseJSON(String levelJSON){
        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(Gdx.files.internal(levelJSON));
        JsonValue tileMapInfo = base.get("tileMapInfo");
        JsonValue levelObjects = base.get("levelObjects");

        tileMap = new TileMap(tileMapInfo.getString("tileMapPath"), tileMapInfo.getString("spriteSheetPath")
            , tileMapInfo.getInt("endFrame"));

        for (JsonValue value: levelObjects){
            switch (value.getString("type")) {
                case "coin":

                    coins.add(new Coin(value.getFloat("x"),value.getFloat("y")));
                    break;
                case "pot":
                    pots.add(new Pot(value.getFloat("x"),value.getFloat("y")));
                    break;
                case "door":
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

    private void update(float delta) {
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        Vector2 velocity = player.getVelocity(up, down, left, right);



        Physics.moveWithTileCollisions(player, tileMap, delta, velocity.x, velocity.y);
        //Physics.playerCollisions(player, tileMap, velocity, delta);

    }



    @Override
    public void show() {
        scene.add(player);
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
				batch.draw(tile.getSprite(), tile.getPos().x, tile.getPos().y, 0,0,16,16,2.5f,2.5f,0);
			}
		}
	}

	for (GameEntity obj: scene){
		batch.draw(obj.getSprite(), obj.getPos().x, obj.getPos().y);
	}
        batch.end();

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

        Player.SPEED = 200f;

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

        if (player.position.x + (player.getWidthPixels() / 2) < 0) {
            Main.lastRoom = true;
        }
        if (player.position.x + (player.getWidthPixels() / 2) > 640) {
            Main.nextRoom = true;
        }

        for (Pot pot : pots) {
            if (Physics.onPot(pot, player, tileMap)) {
                spawnCoin(pot.getPos().x, pot.getPos().y);
            }
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

