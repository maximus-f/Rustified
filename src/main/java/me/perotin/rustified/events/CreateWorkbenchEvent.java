package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrintData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/* Created by Perotin on 2/17/19 */
public class CreateWorkbenchEvent implements Listener {

    private Rustified plugin;

    public CreateWorkbenchEvent(Rustified plugin){
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlaceSign(BlockPlaceEvent event) {
        Block sign = event.getBlock();
        if (sign.getType() == Material.WALL_SIGN) {
            Block against = event.getBlockAgainst();
            BluePrintData data = BluePrintData.getSingleton();



            for (Material m : data.getWorkbenchBlocks()) {
                if (against.getType() == m) {
                    // all possible directions of next block
                    BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                    for (BlockFace face : possibleLocations) {
                        // check for type matching
                        if (against.getRelative(face).getType() == m) {
                            // we got a workbench
                            // this code doesn't work, bad cast. Need to do some researching why

                            Sign levelOne = (Sign) against.getState(); // This line has the issue
                            levelOne.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));

                            Block nextBlock = against.getRelative(face);
                            Sign levelOneTwo = (Sign) nextBlock.getState();
                            levelOneTwo.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));

                        }
                    }
                }

            }
        }
    }
}
