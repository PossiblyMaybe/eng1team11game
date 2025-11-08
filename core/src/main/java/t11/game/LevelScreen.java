package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.ArrayList;

public class LevelScreen extends ScreenAdapter{

    private TileMap tileMap;
    //makes an arraylist for all objects in the scene
    ArrayList<GameEntity> scene = new ArrayList<GameEntity>();
    private SpriteBatch batch;


    private final OrthographicCamera camera;
    private Vector2 target;

    String levelJSON;
    Player player;

    private boolean paused;


    public LevelScreen(String levelJSON, SpriteBatch batch, Player player,
                       OrthographicCamera camera, Viewport viewport) {
        //gets the spritebatch so we only use 1 global batch which will be disposed upon
        //closing the game
        this.batch = batch;
        this.levelJSON = levelJSON;
        this.player = player;
        this.camera = camera;
        this.paused = false;

        player.position.set(0, 200);

        target = new Vector2(320, 240);
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

        //im not using the normal json parser, you can't make me
        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(Gdx.files.internal(levelJSON));
        JsonValue tileMapInfo = base.get("tileMapInfo");
        JsonValue levelObjects = base.get("levelObjects");

        tileMap = new TileMap(tileMapInfo.getString("tileMapPath"), tileMapInfo.getString("spriteSheetPath")
            , tileMapInfo.getInt("endFrame"));

        for (JsonValue value: levelObjects){
            switch (value.getString("type")) {
                case "coin":
                    scene.add(new Coin(value.getFloat("x"), value.getFloat("y")));
                    break;
                case "pot":
                    break;
                case "door":
                    break;
                case "trap":
                    break;
            }
        }

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
        //draw all the tiles
        for (Tile[] tileRow: tileMap.getTiles()){
            for (Tile tile: tileRow){
                if (tile.getSprite() != null) {
                    batch.draw(tile.getSprite(), tile.getPos().x, tile.getPos().y, 0, 0,
                        16, 16, (float)2.5, (float)2.5, 0);
                }
            }
        }
        //draw all the objects
        for (GameEntity obj: scene){
            batch.draw(obj.getSprite(), obj.getPos().x, obj.getPos().y);
        }
        batch.end();
    }

    public void physics(float delta){
        update(delta);

        if (player.position.x + (player.getWidthPixels() / 2) < target.x - 320f) {
            target.x -= 640f;
            CameraStyles.lockOnTarget(camera, target);
        }
        if (player.position.x + (player.getWidthPixels() / 2) > target.x + 320f) {
            target.x += 640f;
            CameraStyles.lockOnTarget(camera, target);
        }
        batch.setProjectionMatrix(camera.combined);
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

