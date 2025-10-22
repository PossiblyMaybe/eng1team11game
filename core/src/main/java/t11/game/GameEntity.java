package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public interface GameEntity {
    //I made this so that the arraylist scene in testscreen works
    //since we aren't using entity this works fine
    public Texture getTexture();
    public Vector2 getPos();
    public Vector2 getScale();
    public float getRotation();
}
