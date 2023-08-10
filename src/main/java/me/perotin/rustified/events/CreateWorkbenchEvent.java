package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.Workbench;
import me.perotin.rustified.objects.WorkbenchLocations;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

/* Created by Perotin on 2/17/19 */
public class CreateWorkbenchEvent implements Listener {

    private Rustified plugin;

    public CreateWorkbenchEvent(Rustified plugin){
        this.plugin = plugin;
    }

    // Needs some fine tuning, currently carries through if sign is placed on a face with a block not next to it.
//    @EventHandler(ignoreCancelled = true)
//    public void onPlaceSign(BlockPlaceEvent event) {
//        Block sign = event.getBlock();
//        if (sign.getType().toString().contains("SIGN")) {
//            Block against = event.getBlockAgainst();
//            BluePrintData data = BluePrintData.getSingleton();
//            WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();
//
//            for (Material m : data.getWorkbenchBlocks()) {
//                if (against.getType() == m) {
//                    // all possible directions of next block
//                    BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
//                    for (BlockFace face : possibleLocations) {
//                        // check for type matching
//                        if (against.getRelative(face).getType() == m) {
//                            int level = data.getLevelForWorkbench(m);
//
//                            Bukkit.getScheduler().runTaskLater(Rustified.getInstance(), new Runnable() {
//                                @Override
//                                public void run() {
//                                    Sign levelOne = (Sign) sign.getState();
//                                    levelOne.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench:"));
//                                    levelOne.setLine(2, "Lv." + level);
//                                    levelOne.update();
//                                }
//                            }, 20); // The '1' here will schedule the update 1 tick later
//
//                            locations.add(new Workbench(sign.getLocation(), level));
//                        }
//                    }
//                }
//            }
//        }
//    }

    @EventHandler(ignoreCancelled = true)
    public void placeSignOnWorkbench(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block sign = event.getBlock();
        if (sign.getType().toString().contains("SIGN")) {
            BluePrintData data = BluePrintData.getSingleton();
            WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();

            BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
            for (BlockFace face : faces) {
                Block against = sign.getRelative(face);

                for (Material m : data.getWorkbenchBlocks()) {
                    if (against.getType() == m) {
                        // all possible directions of next block
                        BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                        for (BlockFace nextFace : possibleLocations) {
                            // check for type matching
                            if (against.getRelative(nextFace).getType() == m) {
                                int level = data.getLevelForWorkbench(m);
                                event.setLine(1, ChatColor.translateAlternateColorCodes('&', "&2&lWorkbench:"));
                                event.setLine(2, "Lv." + level);
                                locations.add(new Workbench(sign.getLocation(), level));
                                player.sendMessage(Messages.getMessage("created-workbench")
                                        .replace("$level$", level+""));
                                return; // exit the loop after the workbench is found
                            }
                        }
                    }
                }
            }
        }
    }



}
