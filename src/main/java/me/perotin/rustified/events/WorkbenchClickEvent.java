package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

/* Created by Perotin on 12/8/19 */
public class WorkbenchClickEvent implements Listener {

    public HashMap<UUID, Inventory> cache;
    private Rustified plugin;

    public WorkbenchClickEvent(Rustified plugin){
        this.plugin = plugin;
        this.cache = new HashMap<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
       // event.setCancelled(true);
        if(event.getWhoClicked() instanceof Player) {
            Player clicker = (Player) event.getWhoClicked();
            if (cache.containsKey(clicker.getUniqueId())
                    && cache.get(clicker.getUniqueId()).equals(event.getClickedInventory())) {
                if(event.getCurrentItem().getType() == Material.TOTEM_OF_UNDYING) {
                    ItemStack recipe = event.getClickedInventory().getItem(2);
                    ItemStack input = event.getClickedInventory().getItem(3);
                    if(recipe.getType() == input.getType()){
                        if(input.getAmount() >= recipe.getAmount()){
                            // success case
                            clicker.closeInventory();
                            int remaining = input.getAmount() - recipe.getAmount();
                            clicker.getInventory().addItem(new ItemStack(input.getType(), remaining));
                            clicker.getInventory().addItem(event.getClickedInventory().getItem(5));
                        } else {
                            clicker.closeInventory();
                            Messages.sendMessage("not-enough-item", "$type$", recipe.getType().toString(), clicker);
                        }
                    } else {
                        clicker.closeInventory();
                        Messages.sendMessage("wrong-type", "$type$", recipe.getType().toString(), clicker);
                    }
                }
            }
        }

    }
}
