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
  "textures": { "wall": "stone.png" },
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
### Optional: grid-based walls
Instead of `collision.rects`, a room can provide `grid` (array of strings).
- `#` = wall, `.` = empty floor
- Lines are written top→down; loader flips to bottom-left origin.
Example:
"size": { "w": 5, "h": 3 },
"grid": [
  "#####",
  "#...#",
  "#####"
]
If `grid` is absent, the loader falls back to `collision.rects`.


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
