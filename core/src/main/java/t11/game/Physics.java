package t11.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *  This class handles the physics and logic in the level screens
 */
public final class Physics {
    private Physics() {}

    /**
     *  This is part of an AABB collision detection system which uses the velocity of the player
     *  and delta time to create a point (dx) predicting the distance the player will move to in a
     *  delta time increment.
     *
     * @param player the player is passed through here
     * @param map the tilemap is passed through here
     * @param dt this is delta time
     * @param vx this is the velocity of the player in the x-axis
     * @param vy this is the velocity of the player in the y-axis
     */
    public static void moveWithTileCollisions(Player player, TileMap map, float dt, float vx, float vy) {
        float tileSize = map.getTileSize();
        Rectangle playerBounds = player.getBounds();

        // X axis:
        float dx = vx * dt;
        // dx is the variable used to store the change in the players x-axis value
        playerBounds.x += dx;
        if (intersectTileSizeSolid(map, playerBounds, tileSize)) {
            // if the players predicted x overlaps with a solid tile the rectangle position is adjusted accordingly on the
            // boundary of the overlapping tile
            if (dx > 0) {
                int tileRight = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / tileSize);
                playerBounds.x = tileRight * tileSize - playerBounds.width;
            } else if (dx < 0) {
                int tileLeft = (int)Math.floor(playerBounds.x / tileSize);
                playerBounds.x = (tileLeft + 1) * tileSize;
            }
        }

        // Y-axis:
        // the above is now mimicked but for the y-axis
        float dy = vy * dt;
        playerBounds.y += dy;
        if (intersectTileSizeSolid(map, playerBounds, tileSize)) {
            if (dy > 0) {
                int tileTop = (int) Math.floor((playerBounds.y + playerBounds.height - 1) / tileSize);
                playerBounds.y = tileTop * tileSize - playerBounds.height;
            } else if (dy < 0) {
                int tileBottom = (int) Math.floor(playerBounds.y / tileSize);
                playerBounds.y = (tileBottom + 1) * tileSize;
            }
        }

        player.position.set(playerBounds.x, playerBounds.y);
    }

    // Checks for overlap

    /**
     *
     * @param map this is the tilemap
     * @param playerBounds this is the players rectangle moved by dx and dy
     * @param tileSize this is the size in pixels of a tile
     * @return this will return a boolean based on whether or not there is an overlap
     */
    private static boolean intersectTileSizeSolid(TileMap map, Rectangle playerBounds, float tileSize) {
        // this finds the tilemap position values (from the .csv) of the overlapped tile
        int minTileX = (int)Math.floor(playerBounds.x / tileSize);
        int maxTileX = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / tileSize);
        int minTileY = (int)Math.floor(playerBounds.y / tileSize);
        int maxTileY = (int)Math.floor((playerBounds.y + playerBounds.height - 1) / tileSize);

        // this cycles through the tiles in the tilemap and returns true (solid) when the overlapped tile is found
        for (int tileY = minTileY; tileY <= maxTileY; tileY++) {
            for (int tileX = minTileX; tileX <= maxTileX; tileX++) {
                if (map.isSolidAt(tileX, tileY)) { return true; }
            }
        }
        return false;
    }

    /**
     *
     * @param player this is the player
     * @param coin this is the coin
     * @return true if collided
     */
    public static boolean coinCollision(Player player, Coin coin) {
        Rectangle playerBounds = player.getBounds();
        Rectangle coinBounds = coin.getBounds();

        return coinBounds.overlaps(playerBounds);
    }

    /**
     *
     * @param player this is the player
     * @param trap this is the trap
     * @return true if collided
     */
    public static boolean onTrap(Player player, Trap trap) {
        Rectangle playerBounds = player.getBounds();
        Rectangle trapBounds = trap.getBounds();

        return playerBounds.overlaps(trapBounds);
    }

    /**
     *
     * @param cooldown this is a delta time countdown until the trap triggers
     * @return returns true if triggered
     */
    public static boolean trapTriggered(float cooldown){
        if (cooldown <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param pot this is the pot
     * @param player this is the player
     * @param map this is the tilemap of the room
     * @return returns true if pot not broken and player colliding and dashing
     */
    public static boolean onPot(Pot pot, Player player, TileMap map){
        Rectangle potBounds = pot.getBounds();
        Rectangle playerBounds = player.getBounds();
        Vector2 potPos =  new Vector2();
        potBounds.getPosition(potPos);

        if (player.getDash() && playerBounds.overlaps(potBounds) && !pot.getBroken()) {
            map.setTileSolid(false, (int)(potPos.x / 40), (int)(potPos.y / 40));
            pot.potBreak();
            return true;
        } else if (pot.getBroken()) {
            return false;
        } else if (!pot.getBroken() && !player.getDash()) {
            map.setTileSolid(true, (int)(potPos.x / 40), (int)(potPos.y / 40));
        }
        return false;

    }
}
