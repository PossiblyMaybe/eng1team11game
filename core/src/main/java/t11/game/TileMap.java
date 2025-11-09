package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileMap {
    private Tile[][] tiles;
    private int width;
    private int height;
    public static final int TILE_SIZE = 40; //2.5x scale for 16 pixel tiles
    private SpriteSheet spriteSheet;

    public int getWidthTiles() { return width; }
    public int getHeightTiles() { return height; }
    public int getTileSize() { return TILE_SIZE; }

    public TileMap(String filePath, String spriteSheetPath, int endFrame) {
        this.spriteSheet = new SpriteSheet(spriteSheetPath, endFrame);
        this.load(filePath);
    }

    private void load(String filePath) {

        //creates a list of lines and assigns the height, width and array size for tile
        String fileData = Gdx.files.internal(filePath).readString().trim();
        String[] lines = fileData.split("\\r?\\n");
        this.height = lines.length;
        this.width = lines[0].split(",").length;
        this.tiles = new Tile[this.width][this.height];

        for(int y = 0; y < this.height; ++y) {
            //creates a list for each individual item in the csv
            String[] parts = lines[y].split(",");

            for(int x = 0; x < this.width; ++x) {
                //grabs the specific value
                String value = parts[x].trim();
                if (!value.isEmpty()) {
                    int tileValue = 0; //default value

                    try {
                        tileValue = Integer.parseInt(value); //tries to grab the value and convert it to int
                    } catch (NumberFormatException var11) {
                        System.err.println("Error parsing value '" + value + "' at (" + x + "," + y + ")");
                    }

                    /*if (tileValue != 0) {
                        this.tiles[x][this.height - y - 1] = new Tile(spriteSheet.getTexture(tileValue), true, (float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                    } else {
                        this.tiles[x][this.height - y - 1] = new Tile(null, false, (float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                    }*/
                    if (tileValue == -1)
                        this.tiles[x][this.height - y - 1] = new Tile(null, false,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                    else if (tileValue >= 11)
                        this.tiles[x][this.height - y - 1] = new Tile(spriteSheet.getTexture(tileValue), true,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                    else
                        this.tiles[x][this.height - y - 1] = new Tile(spriteSheet.getTexture(tileValue), false,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                    /*
                    switch(tileValue) {
                        case -1:
                           this.tiles[x][this.height - y - 1] = new Tile(null, false,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                            break;
                        case 1:
                            this.tiles[x][this.height - y - 1] = new Tile(spriteSheet.getTexture(tileValue), false,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                            break;
                        default:
                            this.tiles[x][this.height - y - 1] = new Tile(spriteSheet.getTexture(tileValue), true,(float)(x * TILE_SIZE), (float)((this.height - y - 1) * TILE_SIZE));
                            break;

                    }
                    */
                }
            }
        }

    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public boolean isSolidAt(int tileX, int tileY) {
        if (tileX >= 0 && tileY >= 0 && tileX < this.width && tileY < this.height) {
            Tile tile = this.tiles[tileX][tileY];
            return tile != null && tile.isSolid();
        } else {
            return false;
        }
    }
}
