package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/* Created by Perotin on 7/29/19 */
public class CancelInfoClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(Rustified.getInstance().getConfig().getString("info-gui-name"))) event.setCancelled(true);
    }
}
