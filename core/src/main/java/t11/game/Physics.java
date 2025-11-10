package t11.game;

import com.badlogic.gdx.math.Rectangle;

public final class Physics {
    private Physics() {}


    public static void moveWithTileCollisions(Player player, TileMap map, float dt, float vx, float vy) {
        float tileSize = map.getTileSize();
        Rectangle playerBounds = player.getBounds();

        // X axis:
        float dx = vx * dt;
        playerBounds.x += dx;
        if (intersectileSizeSolid(map, playerBounds, tileSize)) {
            if (dx > 0) {
                int tileRight = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / tileSize);
                playerBounds.x = tileRight * tileSize - playerBounds.width;
            } else if (dx < 0) {
                int tileLeft = (int)Math.floor(playerBounds.x / tileSize);
                playerBounds.x = (tileLeft + 1) * tileSize;
            }
        }

        // Y axis:
        float dy = vy * dt;
        playerBounds.y += dy;
        if (intersectileSizeSolid(map, playerBounds, tileSize)) {
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
    private static boolean intersectileSizeSolid(TileMap map, Rectangle playerBounds, float tileSize) {
        int mintileX = (int)Math.floor(playerBounds.x / tileSize);
        int maxTileX = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / tileSize);
        int minTileY = (int)Math.floor(playerBounds.y / tileSize);
        int maxTileY = (int)Math.floor((playerBounds.y + playerBounds.height - 1) / tileSize);

        for (int tileY = minTileY; tileY <= maxTileY; tileY++) {
            for (int tileX = mintileX; tileX <= maxTileX; tileX++) {
                if (map.isSolidAt(tileX, tileY)) { return true; }
            }
        }
        return false;
    }

    public static boolean coinCollision(Player player, Coin coin) {
        Rectangle playerBounds = player.getBounds();
        Rectangle coinBounds = coin.getBounds();

        return coinBounds.overlaps(playerBounds);
    }

    public static boolean onTrap(Player player, Trap trap) {
        Rectangle playerBounds = player.getBounds();
        Rectangle trapBounds = trap.getBounds();

        return playerBounds.overlaps(trapBounds);
    }

    public static boolean trapTriggered(float cooldown){
        if (cooldown <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean onPot(Pot pot, Player player, TileMap map){
        Rectangle potBounds = pot.getBounds();
        Rectangle playerBounds = player.getBounds();
        if (player.getDash() && playerBounds.overlaps(potBounds) && !pot.getBroken()) {
            pot.potBreak();
            if (Math.random() > 0.5f) {
                return true;
            }
        } else if (!pot.getBroken() && !player.getDash() && playerBounds.overlaps(potBounds)) {
            map.getTiles();
        }
        return false;
    }
}
