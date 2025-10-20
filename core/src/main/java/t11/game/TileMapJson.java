package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class TileMapJson {

    private Tile[][] tiles;
    private int width, height;        // in tiles
    private int TILE_SIZE = 32;       // pixels per tile

    private Texture wallTex;          // shared textures
    private Texture floorTex;

    public TileMapJson(String jsonPath) {
        load(jsonPath);
    }

    // --- API (engine contract) ---
    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] != null) tiles[x][y].render(batch);
            }
        }
    }

    public boolean isSolid(int gx, int gy) {
        if (gx < 0 || gy < 0 || gx >= width || gy >= height) return false;
        Tile t = tiles[gx][gy];
        return t != null && t.isSolid();
    }

    public int getTileSize() { return TILE_SIZE; }

    // (optional helpers; harmless to keep)
    public int getWidth()  { return width; }
    public int getHeight() { return height; }

    public void dispose() {
        if (wallTex  != null) wallTex.dispose();
        if (floorTex != null) floorTex.dispose();
    }

    // --- Loader ---
    private void load(String jsonPath) {
        try {
            JsonValue root = new JsonReader().parse(Gdx.files.internal(jsonPath));

            // tile size
            TILE_SIZE = root.getInt("tileSize", 32);

            // textures (relative to assets/)
            if (root.has("textures")) {
                JsonValue tex = root.get("textures");
                if (tex.has("wall")  && !tex.getString("wall").isEmpty())
                    wallTex  = new Texture(tex.getString("wall"));
                if (tex.has("floor") && !tex.getString("floor").isEmpty())
                    floorTex = new Texture(tex.getString("floor"));
            }
            if (wallTex == null) wallTex = new Texture("stone.png"); // default

            // first room (minimal sample)
            JsonValue room = root.get("rooms").get(0);
            JsonValue size = room.get("size");
            width  = size.getInt("w");
            height = size.getInt("h");

            tiles = new Tile[width][height];

            // optional floor tiling
            if (floorTex != null) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        tiles[x][y] = new Tile(floorTex, false, x * TILE_SIZE, y * TILE_SIZE);
                    }
                }
            }

            
            if (room.has("grid")) {
                JsonValue grid = room.get("grid");
                // lines are written top->down; convert to bottom-left origin
                for (int row = 0; row < grid.size; row++) {
                    String line = grid.getString(row);
                    int y = height - 1 - row; // flip vertical
                    for (int x = 0; x < Math.min(width, line.length()); x++) {
                        char c = line.charAt(x);
                        if (c == '#') {
                            tiles[x][y] = new Tile(wallTex, true, x * TILE_SIZE, y * TILE_SIZE);
                        } else if (floorTex == null) {
                            // keep null when no floor texture
                            tiles[x][y] = null;
                        }
                    }
                }
            } else {
                // fallback: legacy rectangular walls
                JsonValue rects = room.get("collision").get("rects");
                for (JsonValue rc : rects) {
                    int rx = rc.getInt("x"), ry = rc.getInt("y");
                    int rw = rc.getInt("w"), rh = rc.getInt("h");
                    for (int gx = rx; gx < rx + rw; gx++) {
                        for (int gy = ry; gy < ry + rh; gy++) {
                            int storeY = gy; // keep current convention (no extra flip)
                            tiles[gx][storeY] =
                                new Tile(wallTex, true, gx * TILE_SIZE, storeY * TILE_SIZE);
                        }
                    }
                }
            }

        } catch (Exception e) {
            Gdx.app.error("TileMapJson", "Failed to load: " + jsonPath, e);
            // build a safe tiny world to avoid NPE
            width = height = 1;
            tiles = new Tile[1][1];
        }
    }
}
