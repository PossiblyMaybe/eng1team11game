package t11.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TestScreen extends ScreenAdapter{



    Coin coin1;
    //makes an arraylist for all objects in the scene
    ArrayList<GameEntity> scene = new ArrayList<GameEntity>();
    private SpriteBatch batch;

	public TestScreen(SpriteBatch batchIn){
        //gets the spritebatch so we only use 1 global batch which will be disposed upon
        //closing the game
        batch = batchIn;

	}

    @Override
    public void show() {
        super.show();
        //assigns objects here so we don't use unneccesary memory until we need to show the screen
        coin1 = new Coin(100, 200);
        scene.add(coin1);
    }

    @Override
    public void render(float delta){
        //draws all of the objects
        batch.begin();
        for (int i = 0; i < scene.size(); i++){ //so this doesn't draw it for some reason??? idk why
            batch.draw(scene.get(i).getSprite(), scene.get(i).getPos().x, scene.get(i).getPos().y);
        }
        batch.end();
    }


}

