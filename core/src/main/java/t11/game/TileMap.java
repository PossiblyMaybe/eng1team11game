package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {
    private Tile[][] tiles;
    private int width;
    private int height;
    private static final int TILE_SIZE = 32;
    private Texture stone = new Texture("stone.jpg");

    public TileMap(String filePath) {
        this.load(filePath);
    }

    private void load(String filePath) {
        String fileData = Gdx.files.internal(filePath).readString().trim();
        String[] lines = fileData.split("\\r?\\n");
        this.height = lines.length;
        String[] firstLine = lines[0].split(",");
        this.width = firstLine.length;
        this.tiles = new Tile[this.width][this.height];

        for(int y = 0; y < this.height; ++y) {
            String[] parts = lines[y].split(",");

            for(int x = 0; x < this.width; ++x) {
                String value = parts[x].trim();
                if (!value.isEmpty()) {
                    int tileValue = 0;

                    try {
                        tileValue = Integer.parseInt(value);
                    } catch (NumberFormatException var11) {
                        System.err.println("Error parsing value '" + value + "' at (" + x + "," + y + ")");
                    }

                    if (tileValue == 1) {
                        this.tiles[x][this.height - y - 1] = new Tile(this.stone, true, (float)(x * 32), (float)((this.height - y - 1) * 32));
                    } else {
                        this.tiles[x][this.height - y - 1] = new Tile((Texture)null, false, (float)(x * 32), (float)((this.height - y - 1) * 32));
                    }
                }
            }
        }

    }

    public void render(SpriteBatch batch) {
        for(int x = 0; x < this.width; ++x) {
            for(int y = 0; y < this.height; ++y) {
                if (this.tiles[x][y] != null) {
                    this.tiles[x][y].render(batch);
                }
            }
        }

    }

    public boolean isSolidAt(int tileX, int tileY) {
        if (tileX >= 0 && tileY >= 0 && tileX < this.width && tileY < this.height) {
            Tile tile = this.tiles[tileX][tileY];
            return tile != null && tile.isSolid();
        } else {
            return false;
        }
    }

    public void dispose() {
        this.stone.dispose();
    }
}
