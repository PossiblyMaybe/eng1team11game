# World Data Format v1 (tile-based)

## Location & Units
- Files live in: `assets/worlds/*.json`
- Unit: **tile** (integers). Pixels = tiles × `tileSize`.
- Origin: **bottom-left**.

## Minimal structure (walls / positions / textures)
{
  "formatVersion": "1.0",
  "tileSize": 32,
  "origin": "bottom-left",
  "textures": { "wall": "libgdx.png" },
  "rooms": [
    {
      "id": "room_start",
      "size": { "w": 10, "h": 8 },
      "collision": {
        "rects": [
          { "id": "south", "x": 0,  "y": 0,  "w": 10, "h": 1 },
          { "id": "north", "x": 0,  "y": 7,  "w": 10, "h": 1 },
          { "id": "west",  "x": 0,  "y": 0,  "w": 1,  "h": 8 },
          { "id": "east",  "x": 9,  "y": 0,  "w": 1,  "h": 8 }
        ]
      }
    }
  ]
}

## Engine contract (minimal)
- Loader: `com.mygdx.starter.world.TileMapJson`
- API:
  - `render(SpriteBatch)` — draw floor/walls
  - `isSolid(int gx, int gy)` — collision query on tile grid
  - `getTileSize()` — tile-to-pixel conversion
- Load pipeline:
  1) Read `tileSize` and room `size`.
  2) (Optional) tile the floor texture if provided.
  3) Apply `collision.rects[]` as **solid** walls.
