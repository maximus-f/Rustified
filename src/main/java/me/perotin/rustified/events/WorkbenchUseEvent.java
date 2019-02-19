package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrintData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/* Created by Perotin on 2/18/19 */
public class WorkbenchUseEvent implements Listener {


    private Rustified plugin;

    public WorkbenchUseEvent(Rustified plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player clicker = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if(block != null){
                if(block.getType() == Material.WALL_SIGN) {
                    WallSign s = (WallSign) block.getState();
                    Block against = block.getRelative(s.getFacing());
                    BluePrintData data = BluePrintData.getSingleton();
                    if (data.getWorkbenchBlocks().contains(against.getType())){
                        // potential workbench
                        BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                        for(BlockFace face : possibleLocations){
                            if(against.getRelative(face).getType() == against.getType()){
                                // tis a workbench
                                event.setCancelled(true);
                                Inventory menu = Bukkit.createInventory(null, InventoryType.MERCHANT, "Workbench Level: "+ data.getLevelForWorkbench(against.getType()));
                                clicker.openInventory(menu);

                            }
                        }

                    }
                }
            }
        }
    }
}
