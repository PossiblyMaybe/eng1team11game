package t11.game;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public final class Physics {
    private Physics() {}

    public static void moveWithTileCollisions(Player player, TileMap map, float dt, float vx, float vy) {
        float ts = map.getTileSize();
        Rectangle playerBounds = player.getBounds();

        // X axis:
        float dx = vx * dt;
        playerBounds.x += dx;
        if (intersectsSolid(map, playerBounds, ts)) {
            if (dx > 0) {
                int tileRight = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / ts);
                playerBounds.x = tileRight * ts - playerBounds.width;
            } else if (dx < 0) {
                int tileLeft = (int)Math.floor(playerBounds.x / ts);
                playerBounds.x = (tileLeft + 1) * ts;
            }
        }

        // Y axis:
        float dy = vy * dt;
        playerBounds.y += dy;
        if (intersectsSolid(map, playerBounds, ts)) {
            if (dy > 0) {
                int tileTop = (int) Math.floor((playerBounds.y + playerBounds.height - 1) / ts);
                playerBounds.y = tileTop * ts - playerBounds.height;
            } else if (dy < 0) {
                int tileBottom = (int) Math.floor(playerBounds.y / ts);
                playerBounds.y = (tileBottom + 1) * ts;
            }
        }

        player.position.set(playerBounds.x, playerBounds.y);
    }

    // Checks for overlap
    private static boolean intersectsSolid(TileMap map, Rectangle playerBounds, float ts) {
        int minTX = (int)Math.floor(playerBounds.x / ts);
        int maxTX = (int)Math.floor((playerBounds.x + playerBounds.width - 1) / ts);
        int minTY = (int)Math.floor(playerBounds.y / ts);
        int maxTY = (int)Math.floor((playerBounds.y + playerBounds.height - 1) / ts);

        for (int ty = minTY; ty <= maxTY; ty++) {
            for (int tx = minTX; tx <= maxTX; tx++) {
                if (map.isSolidAt(tx, ty)) { return true; }
            }
        }
        return false;
    }

    public static boolean coinCollision(Player player, Coin coin) {
        Rectangle playerBounds = player.getBounds();
        Rectangle coinBounds = coin.getBounds();

        return coinBounds.overlaps(playerBounds);
    }

    public static boolean trapTriggered(Player player, Trap trap) {
        Rectangle playerBounds = player.getBounds();
        Rectangle trapBounds = trap.getBounds();

        return playerBounds.overlaps(trapBounds);
    }





}
