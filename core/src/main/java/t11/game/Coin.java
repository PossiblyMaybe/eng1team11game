package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Coin implements GameEntity{
    public final Vector2 position = new Vector2();
    private final Vector2 scale = new Vector2(1,1);
    private float rotation = 0;
    private Texture sprite = new Texture("coin.png");

    //it's a coin :)

    public Coin(float x, float y){
        position.x = x;
        position.y = y;
    }

    public void collect()
    {
        //currently doesn't do anything but when hitboxes work we can make this increment the global counter
        //coinCount += 1;
    }

    @Override
    public Texture getSprite() {
        return sprite;
    }

    @Override
    public Vector2 getPos() {
        return position;
    }

    @Override
    public Vector2 getScale() {
        return scale;
    }

    @Override
    public float getRotation() {
        return rotation;
    }
}
