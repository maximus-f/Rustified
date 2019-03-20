package me.perotin.rustified.objects;

import org.bukkit.Location;

/* Created by Perotin on 3/19/19 */

/**
 * Class that represents a single-entity workbench in the world.
 */
public final class Workbench {

    /** Location of the sign on the bench **/
    private final Location location;
    private final int level;

    public Workbench(Location location, int level) {
        this.location = location;
        this.level = level;
    }

    public Location getLocation() {
        return location;
    }

    public int getLevel() {
        return level;
    }
}
