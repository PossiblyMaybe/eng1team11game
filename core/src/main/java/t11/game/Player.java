package t11.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Player implements GameEntity{
    public final Vector2 position = new Vector2(0,0);
    private final Vector2 scale = new Vector2(  1.5f,1.5f);
    private float rotation = 0;
    private TextureRegion sprite = new TextureRegion(new Texture("bob.png"));

    private final static float SPEED = 400;
    private final static float DASHMULT = 2;
    private boolean isDashing = false;
    private float dashCooldown = 0;
    private final Vector2 lastDirection = new Vector2();



    public Player()
    {

    }

    @Override
    public TextureRegion getSprite() { return sprite; }

    @Override
    public Vector2 getPos() { return position; }

    @Override
    public Vector2 getScale() { return scale; }

    @Override
    public float getRotation() { return rotation; }


    public void move(Vector2 direction, boolean dashTry, float delta) {
        /* Takes a Vector2 for direction, a boolean to check if the player is dashing and
        delta which is deltaTime. If the player is dashing then their speed is multiplied by DASHMULT
        */

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
}
