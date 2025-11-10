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

    public void setSolid(boolean solid){
        this.isSolid = !isSolid;
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

    }
}
