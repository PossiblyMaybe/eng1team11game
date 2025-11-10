package t11.game;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * This is the player class and it handles movement and dashing of the plauer
 */
public class Player implements GameEntity{
    public final Vector2 position = new Vector2(0,0);
    private static final Vector2 scale = new Vector2(2f,2f);
    private float rotation = 0;
    private final SpriteSheet sprite = new SpriteSheet("entities.png", 5);

    public final float SPEED = 200f;
    private final float DASHMULT = 2;
    private boolean isDashing = false;
    private float dashCooldown = 0;
    public final Vector2 lastDirection = new Vector2();

    public float getWidthPixels() { return sprite.getTexture().getRegionWidth() * scale.x; }
    public float getHeightPixels() { return (sprite.getTexture().getRegionHeight()-2) * scale.y; }
    //note the sprite here has a height of 14 pixels not 16


    public Player() { }

    /**
     *
     * @return returns a rectangle used for the players hitbox
     */
    public Rectangle getBounds() { return new Rectangle(position.x, position.y, getWidthPixels(), getHeightPixels()); }

    /**
     *
     * @param up is the player pressing up
     * @param down is the player pressing down
     * @param left is the player pressing left
     * @param right is the player pressing right
     * @return returns the players direction
     */
    public Vector2 getVelocity(boolean up, boolean down, boolean left, boolean right) {
        float velocityX = (right ? 1f : 0f) - (left ? 1f : 0f);
        float velocityY = (up ? 1f : 0f) - (down ? 1f : 0f);
        if (velocityX != 0 || velocityY != 0) {
            float lengthInverse = 1f / (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            velocityX *= SPEED * lengthInverse;
            velocityY *= SPEED * lengthInverse;
        }
        return new Vector2(velocityX, velocityY);
    }

    public boolean getDash() { return isDashing; }

    @Override
    public TextureRegion getSprite() { return sprite.getTexture(); }

    @Override
    public Vector2 getPos() { return position; }

    @Override
    public Vector2 getScale() { return scale; }

    @Override
    public float getRotation() { return rotation; }

    /**
     * Handles player movement
     * @param direction the current direction the player is going in
     * @param dashTry if the player is trying to dash
     * @param delta deltaTime
     * @param paused is the game paused
     */
    public void move(Vector2 direction, boolean dashTry, float delta, boolean paused) {
        /* Takes a Vector2 for direction, a boolean to check if the player is dashing and
        delta which is deltaTime. If the player is dashing then their speed is multiplied by DASHMULT
        */
        if (paused)
            return;

        if (dashTry && dashCooldown <= 0) {
            isDashing = true;
            dashCooldown = 5;
        }


        if (dashCooldown >= 4.5 && isDashing) {
            position.x += lastDirection.x * DASHMULT * SPEED * delta;
            position.y += lastDirection.y * DASHMULT * SPEED * delta;

        } else {
            position.x += direction.x * SPEED * delta;
            position.y += direction.y * SPEED * delta;
            lastDirection.x = direction.x;
            lastDirection.y = direction.y;
            isDashing = false;
        }

        dashCooldown -= delta;
    }

    @Override
    public void dispose(){
        sprite.dispose();
    }
}
