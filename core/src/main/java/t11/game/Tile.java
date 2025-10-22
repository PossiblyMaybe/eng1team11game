package t11.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    private Texture texture;
    private boolean solid;
    private float x, y;
    private static final int TILE_SIZE = 32;

    public Tile(Texture texture, boolean solid, float x, float y) {
        this.texture = texture;
        this.solid = solid;
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        if (texture != null) {
            batch.draw(texture, x, y, TILE_SIZE, TILE_SIZE);
        }
        batch.end();
    }

    public boolean isSolid() {
        return solid;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
