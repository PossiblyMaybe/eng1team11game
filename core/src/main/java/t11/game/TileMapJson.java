package t11.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class TileMapJson {
    private Tile[][] tiles;
    private int width, height;
    private int TILE_SIZE = 32;
    private Texture wallTex, floorTex;

    public TileMapJson(String jsonPath) { load(jsonPath); }

    private void load(String jsonPath) {
        try {
            JsonValue root = new JsonReader().parse(Gdx.files.internal(jsonPath));
            TILE_SIZE = root.getInt("tileSize", 32);

            // textures
            if (root.has("textures")) {
                JsonValue tex = root.get("textures");
                if (tex.has("wall"))  wallTex  = new Texture(tex.getString("wall"));
                if (tex.has("floor") && !tex.get("floor").isNull())
                    floorTex = new Texture(tex.getString("floor"));
            }
            if (wallTex == null) wallTex = new Texture("stone.png"); // fallback

            // first room
            JsonValue room = root.get("rooms").get(0);
            JsonValue size = room.get("size");
            width  = size.getInt("w");
            height = size.getInt("h");

            tiles = new Tile[width][height];

            // optional floor
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    tiles[x][y] = new Tile(floorTex, false, x * TILE_SIZE, y * TILE_SIZE);
                }
            }

            // walls from collision rects
            for (JsonValue rc : room.get("collision").get("rects")) {
                int rx = rc.getInt("x"), ry = rc.getInt("y");
                int rw = rc.getInt("w"), rh = rc.getInt("h");
                for (int gx = rx; gx < rx + rw; gx++) {
                    for (int gy = ry; gy < ry + rh; gy++) {
                        int storeY = gy; // flip if needed -> height - 1 - gy
                        tiles[gx][storeY] =
                                new Tile(wallTex, true, gx * TILE_SIZE, storeY * TILE_SIZE);
                    }
                }
            }
        } catch (Exception e) {
            Gdx.app.error("TileMapJson", "Failed to load " + jsonPath, e);
            width = height = 1;
            tiles = new Tile[1][1];
            tiles[0][0] = new Tile(null, false, 0, 0);
        }
    }

    public void render(SpriteBatch batch) {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (tiles[x][y] != null) tiles[x][y].render(batch);
    }

    public boolean isSolid(int gx, int gy) {
        if (gx < 0 || gy < 0 || gx >= width || gy >= height) return false;
        Tile t = tiles[gx][gy];
        return t != null && t.isSolid();
    }

    public int getTileSize() { return TILE_SIZE; }

    public void dispose() {
        if (wallTex != null) wallTex.dispose();
        if (floorTex != null && floorTex != wallTex) floorTex.dispose();
    }
}
