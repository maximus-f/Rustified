package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.commands.ResearchCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

/* Created by Perotin on 8/9/19 */
public class ResearchClickEvent implements Listener {

    private Rustified plugin;

    public ResearchClickEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if(event.getWhoClicked() instanceof Player){
            Player clicker = (Player) event.getWhoClicked();
            if(ResearchCommand.researching.containsKey(clicker.getUniqueId())){
                String title = ResearchCommand.researching.get(clicker.getUniqueId());
                ItemStack clicked = event.getCurrentItem();
                if(clicked == null || clicked.getType() == Material.AIR) return;



                if(event.getView().getTitle().equals(title) && event.getSlot() == 2 && clicked.getType() == Material.PAPER && clicked.hasItemMeta()){
                    // were in
                    Material type = Material.valueOf(ChatColor.stripColor(clicked.getItemMeta().getDisplayName().split(" ")[2]));
                    if(!clicker.getInventory().contains(new ItemStack(type)) && event.getSlot() == 2 && clicked.getType() == Material.PAPER && clicked.hasItemMeta()){
                        event.setCancelled(true);
                        ResearchCommand.researching.remove(clicker.getUniqueId());
                        clicker.closeInventory();
                        return;
                    }
                    if(event.getSlot() == 2 && clicked.getType() == Material.PAPER && clicked.hasItemMeta()){
                        // remove the item
                        consumeItem(clicker, 1, type);
                    }
                }
            }
        }
    }

    public static boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

       // player.updateInventory();
        return true;
    }

}
