package t11.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class Player implements GameEntity{
    public final Vector2 position = new Vector2(0,0);
    private int zIndex = 0;
    private final Vector2 scale = new Vector2(  1.5f,1.5f);
    private float rotation = 0;
    private final Texture sprite = new Texture("bob.png");

    private final static float SPEED = 400;
    private final static float DASHMULT = 2;



    public Player()
    {

    }


    public void move(Vector2 direction, boolean dash, float delta)
    {
        /* Takes a Vector2 for direction, a boolean to check if the player is dashing and
        delta which is deltaTime. If the player is dashing then their speed is multiplied by DASHMULT
        */

        if (dash) {
            position.x += direction.x * DASHMULT * SPEED * delta;
            position.y += direction.y * DASHMULT * SPEED * delta;
        } else {
            position.x += direction.x * SPEED * delta;
            position.y += direction.y * SPEED * delta;
        }
    }

    @Override
    public Texture getSprite()
    {
        return sprite;
    }

    @Override
    public Vector2 getPos()
    {
        return position;
    }

    @Override
    public Vector2 getScale()
    {
        return scale;
    }

    @Override
    public float getRotation()
    {
        return rotation;
    }


}
