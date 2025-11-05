package t11.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin implements GameEntity{
    public final Vector2 position = new Vector2();
    private final Vector2 scale = new Vector2(1,1);
    private float rotation = 0;
    private SpriteSheet sprite = new SpriteSheet("testSpriteSheet.png", 8);
    private float height;
    private float width;


    //it's a coin :)

    public Coin(float x, float y){
        position.x = x;
        position.y = y;
        height = getSprite().getRegionHeight();
        width = getSprite().getRegionWidth();

    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, width, height);
    }

    @Override
    public TextureRegion getSprite() {
        return sprite.getTexture();
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
