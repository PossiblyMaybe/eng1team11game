package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class LevelScreen extends ScreenAdapter{

    private TileMap tileMap;
    //makes an arraylist for all objects in the scene
    ArrayList<GameEntity> scene = new ArrayList<GameEntity>();
    private SpriteBatch batch;

    String levelJSON;
    Player player;


	public LevelScreen(String levelJSON, SpriteBatch batch, Player player){
        //gets the spritebatch so we only use 1 global batch which will be disposed upon
        //closing the game
        this.batch = batch;
        this.levelJSON = levelJSON;
        this.player = player;
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
        //TODO: Make this dispose otherwise we get a shit ton of memory leaks
    }

    @Override
    public void render(float delta){
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

}

