package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
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
    public void onPlaceSign(BlockPlaceEvent event){
        Player placer = event.getPlayer();
        Block sign = event.getBlock();
        if(sign.getType() == Material.WALL_SIGN){
            Block against = event.getBlockAgainst();
            if(against.getType() == Material.IRON_BLOCK){
                BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                for(BlockFace face : possibleLocations){
                    if(against.getRelative(face).getType() == Material.IRON_BLOCK){
                        // we got a workbench
                        Sign levelOne = (Sign) against.getState();
                        levelOne.setLine(0, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));

                        Block nextBlock = against.getRelative(face);
                        Sign levelOneTwo = (Sign) nextBlock.getState();
                        levelOneTwo.setLine(0, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench Level: &01"));


                    }
                }
            }

        }
    }
}
