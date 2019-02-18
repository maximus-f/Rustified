package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
                    WallSign wallSign = (WallSign) block;
                    //Block against = wallSign.
//                    BluePrintData data = BluePrintData.getSingleton();
//                    if (data.getWorkbenchBlocks().contains(block))
                }
            }
        }
    }
}
