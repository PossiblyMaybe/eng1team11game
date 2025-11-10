package t11.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Since we didn't want to use Entity we made our own interface to group together all
 * the objects that would be drawn to the screen.
 */
public interface GameEntity {
    //I made this so that the arraylist scene in testscreen works
    //since we aren't using entity this works fine
    TextureRegion getSprite();
    Vector2 getPos();
    Vector2 getScale();
    float getRotation();
    Rectangle getBounds();
    void dispose();
}
