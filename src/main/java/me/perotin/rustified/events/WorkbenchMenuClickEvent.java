package me.perotin.rustified.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/* Created by Perotin on 2/18/19 */
public class WorkbenchMenuClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player clicker = (Player) event.getWhoClicked();
            Inventory clicked = event.getClickedInventory();

            boolean namesMatch = false;
            for(int x = 1; x < 3; x++){
                // check this deprecatedness
                if(event.getView().getTitle().equals("Workbench Level: "+ x)) namesMatch = true;
            }

            if(namesMatch){
                // in the inventory
                event.setCancelled(true);

            }

        }
    }
}
