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

import java.util.ArrayList;
import java.util.List;

/* Created by Perotin on 2/17/19 */
public class CreateWorkbenchEvent implements Listener {

    private Rustified plugin;

    public CreateWorkbenchEvent(Rustified plugin){
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlaceSign(BlockPlaceEvent event) {
        Player placer = event.getPlayer();
        Block sign = event.getBlock();
        if (sign.getType() == Material.WALL_SIGN) {
            Block against = event.getBlockAgainst();
            BluePrintData data = BluePrintData.getSingleton();



            for (Material m : data.getWorkbenchBlocks()) {
                if (against.getType() == m) {
                    BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                    for (BlockFace face : possibleLocations) {
                        if (against.getRelative(face).getType() == m) {
                            // we got a workbench
                            event.setCancelled(true);
                            Sign levelOne = (Sign) against.getState().getData();
                            levelOne.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));

                            Block nextBlock = against.getRelative(face);
                            Sign levelOneTwo = (Sign) nextBlock.getState().getData();
                            levelOneTwo.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));

                        }
                    }
                }

            }
        }
    }
}
