package t11.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pot implements GameEntity{
    public final Vector2 position = new Vector2();
    private final Vector2 scale = new Vector2(2.5f,2.5f);
    private float rotation = 0;
    private SpriteSheet sprite = new SpriteSheet("entities.png", 8);
    private float height;
    private float width;
    private boolean broken;

    // it's a pot

    public Pot (float x, float y){
        position.x = x;
        position.y = y;
        height = getSprite().getRegionHeight() * scale.y;
        width = getSprite().getRegionWidth() *  scale.x;
        sprite.setFrame(3);
        broken = false;
    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, width, height);
    }

    public boolean getBroken(){
        return broken;
    }

    public void potBreak(){
        sprite.setFrame(4);
        broken = true;
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

    @Override
    public void dispose(){}
}
