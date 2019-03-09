package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/* Created by Perotin on 2/17/19 */
public class CreateWorkbenchEvent implements Listener {

    private Rustified plugin;

    public CreateWorkbenchEvent(Rustified plugin){
        this.plugin = plugin;
    }

    // Needs some fine tuning, currently carries through if sign is placed on a face with a block not next to it.
    @EventHandler(ignoreCancelled = true)
    public void onPlaceSign(BlockPlaceEvent event) {
        Block sign = event.getBlock();
        if (sign.getType() == Material.WALL_SIGN) {
            Block against = event.getBlockAgainst();
            BluePrintData data = BluePrintData.getSingleton();
            WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();


            for (Material m : data.getWorkbenchBlocks()) {
                if (against.getType() == m) {
                    // all possible directions of next block
                    BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                    for (BlockFace face : possibleLocations) {
                        // check for type matching
                        if (against.getRelative(face).getType() == m) {
                            int level = data.getLevelForWorkbench(m);
                            Sign levelOne = (Sign) sign.getState();
                            levelOne.setEditable(true);
                            levelOne.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench:"));
                            levelOne.setLine(2, "Lv."+level);
                            levelOne.update();
                            locations.add(levelOne.getLocation());
                        }
                    }
                }

            }
        }
    }
}
