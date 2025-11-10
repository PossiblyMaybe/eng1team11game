package t11.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The class used for traps
 */
public class Trap implements GameEntity {
    public final Vector2 position = new Vector2();
    private final Vector2 scale = new Vector2(2.5f,2.5f);
    private final float rotation = 0;
    private SpriteSheet sprite = new SpriteSheet("spriteSheet.png", 87);
    private float height;
    private float width;
    public float cooldown;

    /**
     *
     * @param x the x coordinate of the trap
     * @param y the y coordinate of the trap
     */
    public Trap(float x, float y) {
        cooldown = 1f;
        position.x = x;
        position.y = y;
        height = getSprite().getRegionHeight() * scale.y;
        width = getSprite().getRegionWidth() * scale.x;
        sprite.setFrame(5);
    }

    /**
     *
     * @return returns the rectangle used in the hitbox of the trap
     */
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

    @Override
    public void dispose(){
        sprite.dispose();
    }

}
