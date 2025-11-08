package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {
    private TextureRegion sprite;
    private boolean isSolid;
    private final Vector2 position = new Vector2();

    public Tile(TextureRegion textureReg, boolean solid, float x, float y) {
        this.sprite = textureReg;
        this.isSolid = solid;
        position.x = x;
        position.y = y;
    }

    public Vector2 getPos(){
        return position;
    }

    public TextureRegion getSprite(){
        return sprite;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void dispose() {
        //texture region doesn't have a dispose method so idk what to put here
    }
}
