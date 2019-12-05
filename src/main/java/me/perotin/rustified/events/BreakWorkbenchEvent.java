package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/* Created by Perotin on 3/17/19 */
public class BreakWorkbenchEvent implements Listener {

    private Rustified plugin;

    public BreakWorkbenchEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player destroyer = event.getPlayer();
        WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();
        Block broken = event.getBlock();
        if(broken.getState().getType().toString().contains("SIGN") && locations.getSignLocations().contains(broken.getLocation())){
            // its a workbench
            locations.remove(locations.getWorkbenchWith(broken.getLocation()));
        }
    }
}
